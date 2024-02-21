import React, { useState, useRef, useEffect } from 'react';
import { Label } from 'semantic-ui-react';
import * as Yup from 'yup';
import './ReportRegistration.css';
import ReportService from '../services/reportService';
import { useNavigate, useParams } from 'react-router-dom';
import { Button, Message } from 'semantic-ui-react';
import DeleteConfirmation from './DeleteConfirmation';
import { useSelector, useDispatch } from 'react-redux';
import { setLoading } from '../store/features/reportList/reportsSlice';
import { Loader } from 'semantic-ui-react';

export default function ReportDetail() {

  let { id } = useParams();

  const loading = useSelector(state => state.reports.loading);

  const dispatch = useDispatch();

  const fileInputRef = useRef();

  const navigate = useNavigate();

  const [isReadOnly, setIsReadOnly] = useState(true);

  const [confirmOpen, setConfirmOpen] = useState(false);

  useEffect(() => {
    let reportService = new ReportService();
    dispatch(setLoading(true));
    reportService.getByReportId(id).then(result => {

      if (parseInt(localStorage.getItem('userId'), 10) === -1) {
        setIsReadOnly(false);
      } else {
        setIsReadOnly(parseInt(localStorage.getItem('userId'), 10) !== result.data.laborantId);
      }

      setFormData({
        id: result.data.id,
        patientFirstName: result.data.patientFirstName,
        patientLastName: result.data.patientLastName,
        patientIdNumber: result.data.patientIdNumber,
        reportDate: result.data.reportDate,
        diagnosisDetails: result.data.diagnosisDetails,
        diagnosisTitle: result.data.diagnosisTitle,
        fileNumber: result.data.fileNumber,
        reportPicture: result.data.reportPictureBase64,
        laborantId: result.data.laborantId,
      });
      setReportPicturePreview(`data:image/png;base64,${result.data.reportPictureBase64}`);
      dispatch(setLoading(false));
    });
  }, [id]);

  const schema = Yup.object({
    patientFirstName: Yup.string().required('Required'),
    patientLastName: Yup.string().required('Required'),
    patientIdNumber: Yup.string().matches(/^\d{11}$/, 'Patient Id must be exactly 11 digits').required('Required'),
    diagnosisDetails: Yup.string().required('Required'),
    diagnosisTitle: Yup.string().required('Required'),
    fileNumber: Yup.string().required('Required'),
    reportDate: Yup.date().required('Required'),
    reportPicture: Yup.mixed(),
  });


  const [formData, setFormData] = useState({
    id: 0,
    patientFirstName: '',
    patientLastName: '',
    patientIdNumber: '',
    reportDate: null,
    diagnosisDetails: '',
    diagnosisTitle: '',
    fileNumber: '',
    reportPicture: '',
    laborantId: 0,
  });

  const [formErrors, setFormErrors] = useState({});

  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
    try {
      schema.validateSyncAt(name, { [name]: value });
      setFormErrors({ ...formErrors, [name]: undefined });
    } catch (validationError) {
      setFormErrors({ ...formErrors, [name]: validationError.message });
    }
  };

  const [reportPicturePreview, setReportPicturePreview] = useState('');

  const handleFileChange = (e) => {
    const file = e.target.files[0];

    if (file) {
      const reader = new FileReader();

      reader.onloadend = () => {
        setReportPicturePreview(reader.result);
        setFormData({ ...formData, reportPicture: reader.result });
      };

      reader.readAsDataURL(file);
    } else {
      setReportPicturePreview('');
      setFormData({ ...formData, reportPicture: '' });
    }
  };

  const handleClick = () => {
    fileInputRef.current.click();
  };

  const handleDelete = async () => {
    try {
      await schema.validate(formData, { abortEarly: false });

      let reportService = new ReportService();
      try {
        const response = await reportService.deleteReport(formData.id);
        setError('Report Deleted');

        setTimeout(() => {
          navigate(-1)
          setError(null);
        }, 400);

      } catch (error) {
        console.error('Error :', error);
      }
    } catch (validationError) {
      const errors = {};
      validationError.inner.forEach((error) => {
        errors[error.path] = error.message;
      });
      setFormErrors(errors);
      console.error('Validation error:', validationError.errors);
    }
  };


  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      await schema.validate(formData, { abortEarly: false });

      let reportService = new ReportService();
      try {
        const response = await reportService.updateReport(formData);

        setError('Success')
        setTimeout(() => {
          setError(null)
        }, 500);

      } catch (error) {
        console.error('Error :', error);
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
    loading ? (
      <Loader active={loading} inline='centered' />
    ) : (
      <div className="report-registration-container">
        <div className="column">
          <label>
            Report Picture
            <div className="report-picture-container" onClick={handleClick}>
              {reportPicturePreview || formData.reportPicture ? (
                <img
                  src={reportPicturePreview || formData.reportPicture}
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
              disabled={isReadOnly}
            />
          </label>
        </div>
        <div className="column-location">
          <label>
            File Number
            <input type="text" name="fileNumber" value={formData.fileNumber} onChange={handleChange} disabled={isReadOnly} />
            {formErrors.fileNumber && (
              <Label pointing basic color="red" content={formErrors.fileNumber}></Label>
            )}
          </label>

          <label>
            Patient Id
            <input name="patientIdNumber" value={formData.patientIdNumber} onChange={handleChange} disabled={isReadOnly} />
            {formErrors.patientIdNumber && (
              <Label pointing basic color="red" content={formErrors.patientIdNumber}></Label>
            )}
          </label>

          <label>
            First Name
            <input type="text" name="patientFirstName" value={formData.patientFirstName} onChange={handleChange} disabled={isReadOnly} />
            {formErrors.patientFirstName && (
              <Label pointing basic color="red" content={formErrors.patientFirstName}></Label>
            )}
          </label>

          <label>
            Last Name
            <input type="text" name="patientLastName" value={formData.patientLastName} onChange={handleChange} disabled={isReadOnly} />
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
              disabled={isReadOnly}
            />
            {formErrors.reportDate && (
              <Label pointing basic color="red" content={formErrors.reportDate}></Label>
            )}
          </label>

        </div>
        <div className="column">
          <label>
            Diagnosis Title
            <input type="text" name="diagnosisTitle" value={formData.diagnosisTitle} onChange={handleChange} disabled={isReadOnly} />
            {formErrors.diagnosisTitle && (
              <Label pointing basic color="red" content={formErrors.diagnosisTitle}></Label>
            )}
          </label>

          <label>
            Diagnosis Details
            <textarea name="diagnosisDetails" value={formData.diagnosisDetails} onChange={handleChange} disabled={isReadOnly} />
            {formErrors.diagnosisDetails && (
              <Label pointing basic color="red" content={formErrors.diagnosisDetails}></Label>
            )}
          </label>

          <div>
            {
              parseInt(localStorage.getItem('userId'), 10) === -1 ? (
                <Button.Group style={{ marginRight: '135px', marginTop: '15px', marginBottom: '10px' }}>
                  <Button negative onClick={() => setConfirmOpen(true)}>
                    Delete
                  </Button>
                  <Button.Or text='' />
                  <Button positive onClick={handleUpdate}>
                    Update
                  </Button>
                </Button.Group>) : (
                isReadOnly === false ? (
                  <Button positive onClick={handleUpdate} style={{ marginLeft: '14px', marginTop: '15px', marginBottom: '10px' }}>
                    Update
                  </Button>) : null
              )
            }
            {error === "Success" ? (
              <Message positive>
                <Message.Header>Success</Message.Header>
                <p>Report Update Successful</p>
              </Message>
            ) : (
              error !== null ? (
                <Message negative>
                  <Message.Header>Error</Message.Header>
                  <p>{error}</p>
                </Message>
              ) : null
            )}
            <DeleteConfirmation open={confirmOpen} setOpen={setConfirmOpen} onConfirm={handleDelete} />
          </div>
        </div>
      </div >
    )
  );
}
