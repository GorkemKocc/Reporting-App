import React, { useState, useRef } from 'react';
import { Message, Label } from 'semantic-ui-react';
import * as Yup from 'yup';
import './ReportRegistration.css'
import ReportService from '../services/reportService';
import { useNavigate, useParams } from 'react-router-dom';

export default function ReportRegistration() {

  let { laborantId } = useParams()

  const navigate = useNavigate();

  const fileInputRef = useRef();

  const schema = Yup.object({
    patientFirstName: Yup.string().required('Required'),
    patientLastName: Yup.string().required('Required'),
    patientIdNumber: Yup.string().matches(/^\d{11}$/, 'Patient Id must be exactly 11 digits').required('Required'),
    diagnosisDetails: Yup.string().required('Required'),
    diagnosisTitle: Yup.string().required('Required'),
    fileNumber: Yup.string().required('Required'),
    reportDate: Yup.date().required('Required'),

    reportPicture: Yup.mixed().required('Report picture is required'),

  });

  const [formData, setFormData] = useState({
    patientFirstName: '',
    patientLastName: '',
    patientIdNumber: '',
    reportDate: null,
    diagnosisDetails: '',
    diagnosisTitle: '',
    fileNumber: '',
    reportPicture: null,
    laborantId: parseInt(localStorage.getItem('userId'), 10),
  });

  const [error, setError] = useState(null);

  const [buttonState, setButtonState] = useState(null);

  const [formErrors, setFormErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    setError(null);
    try {
      schema.validateSyncAt(name, { [name]: value });
      setFormErrors({ ...formErrors, [name]: undefined });
    } catch (validationError) {
      setFormErrors({ ...formErrors, [name]: validationError.message });
    }
  };

  const [reportPicturePreview, setReportPicturePreview] = useState(
    process.env.PUBLIC_URL + '/report.jpg'
  );

  const handleFileChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      const reader = new FileReader();

      reader.onloadend = () => {
        setReportPicturePreview(reader.result);
        setFormData({ ...formData, reportPicture: reader.result });
        setFormErrors({ ...formErrors, reportPicture: undefined });
      };

      reader.readAsDataURL(file);
    } else {
      setReportPicturePreview(process.env.PUBLIC_URL + '/report.jpg');
      setFormData({ ...formData, reportPicture: null });
    }
  };

  const handleClick = () => {
    fileInputRef.current.click();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await schema.validate(formData, { abortEarly: false });
      setButtonState('Disable')
      let reportService = new ReportService();

      const reportExists = await reportService.doesReportExist(formData.fileNumber);

      if (reportExists.data) {
        setError('Report with File Number Already Exists');
        setButtonState(null);
      } else {
        try {
          const response = reportService.addReport(formData);
          setError('Success')
          setTimeout(() => {
            navigate(`/laborant/${laborantId}`);
            setButtonState(null);
          }, 500);
        } catch (error) {
          console.error('Error adding report:', error);
        }
      }
    } catch (validationError) {
      const errors = {};
      validationError.inner.forEach((error) => {
        errors[error.path] = error.message;
      });
      setFormErrors(errors);
      setError('Form has errors, registration failed.')
      console.error('Validation error:', validationError.errors);
    }
  };

  return (
    <div className="report-registration-container">
      <div className="column">
        <label>
          Report Picture
          <div className="report-picture-container" >
            <button onClick={handleClick} style={{ width: 0, height: 0, padding: 0, margin: 0, border: 'none' }} />
            {reportPicturePreview ? (
              <img
                src={reportPicturePreview}
                alt="Report Preview"
                className="report-preview"
              />
            ) : (
              <div className="empty-report">
                <i className="fas fa-user"></i>
              </div>
            )}
          </div>
          <input
            type="file"
            ref={fileInputRef}
            onChange={handleFileChange}
            style={{ display: 'none' }}
          />
          {formErrors.reportPicture && (
            <Label pointing basic color="red" content={formErrors.reportPicture}></Label>
          )}
        </label>
      </div>
      <div className="column-location">
        <label>
          File Number
          <input type="text" name="fileNumber" value={formData.fileNumber} onChange={handleChange} />
          {formErrors.fileNumber && (
            <Label pointing basic color="red" content={formErrors.fileNumber}></Label>
          )}
        </label>

        <label>
          Patient Id
          <input name="patientIdNumber" value={formData.patientIdNumber} onChange={handleChange} />
          {formErrors.patientIdNumber && (
            <Label pointing basic color="red" content={formErrors.patientIdNumber}></Label>
          )}
        </label>

        <label>
          First Name
          <input type="text" name="patientFirstName" value={formData.patientFirstName} onChange={handleChange} />
          {formErrors.patientFirstName && (
            <Label pointing basic color="red" content={formErrors.patientFirstName}></Label>
          )}
        </label>

        <label>
          Last Name
          <input type="text" name="patientLastName" value={formData.patientLastName} onChange={handleChange} />
          {formErrors.patientLastName && (
            <Label pointing basic color="red" content={formErrors.patientLastName}></Label>
          )}
        </label>
        <label>
          Report Date
          <input
            type="date"
            name="reportDate"
            value={formData.reportDate || ''}
            onChange={(e) => {
              handleChange(e);
            }}
            className="report-date-text"
          />
          {formErrors.reportDate && (
            <Label pointing basic color="red" content={formErrors.reportDate}></Label>
          )}
        </label>

      </div>
      <div className="column">
        <label>
          Diagnosis Title
          <input type="text" name="diagnosisTitle" value={formData.diagnosisTitle} onChange={handleChange} />
          {formErrors.diagnosisTitle && (
            <Label pointing basic color="red" content={formErrors.diagnosisTitle}></Label>
          )}
        </label>

        <label>
          Diagnosis Details
          <textarea name="diagnosisDetails" value={formData.diagnosisDetails} onChange={handleChange} />
          {formErrors.diagnosisDetails && (
            <Label pointing basic color="red" content={formErrors.diagnosisDetails}></Label>
          )}
        </label>

        <form onSubmit={handleSubmit}>
          <button type="submit" className={`button-width ${buttonState === 'Disable' ? 'disabled' : ''}`} disabled={buttonState === 'Disable'}>Submit</button>
        </form>
        {error === "Success" ? (
          <Message positive>
            <Message.Header>Success</Message.Header>
            <p>Laborant Registration Successful</p>
          </Message>
        ) : (
          error !== null ? (
            <Message negative>
              <Message.Header>Error</Message.Header>
              <p>{error}</p>
            </Message>
          ) : null
        )}
      </div>
    </div>
  );

}
