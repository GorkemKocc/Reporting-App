import React, { useEffect, useState } from 'react';
import { Input, Menu, Icon, Message } from 'semantic-ui-react'
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import ReportService from '../services/reportService';
import * as yup from 'yup';
import { useSelector, useDispatch } from 'react-redux';
import { setReports, setLoading, setActivePage, setIsSearched, setTotalPages } from '../store/features/reportList/reportsSlice';


export default function Navi() {

    let { laborantId } = useParams()

    const dispatch = useDispatch();
    const isSearched = useSelector(state => state.reports.isSearched);
    const pageSize = useSelector(state => state.reports.pageSize);
    const activePage = useSelector(state => state.reports.activePage);
    const ascendingOrder = useSelector(state => state.reports.ascendingOrder);

    const [selectedItem, setSelectedItemName] = useState('');

    const laborantNameSchema = yup.object().shape({
        laborantName: yup.string()
            .required("Laborant name is required.")
    });

    const patientNameSchema = yup.object().shape({
        patientName: yup.string()
            .required("Patient name is required.")

    });

    const patientIdSchema = yup.object().shape({
        patientId: yup.string().matches(/^\d{11}$/, 'Patient Id must be exactly 11 digits').required('Required'),
    });

    const location = useLocation();

    const navigate = useNavigate();

    const [inputValue, setInputValue] = useState('');

    const [error, setError] = useState(null);

    const [isReportsPage, setIsReportsPage] = React.useState(false);

    useEffect(() => {

        setIsReportsPage(location.pathname.includes('/reports') || location.pathname.includes('/reportRegistration') || location.pathname.includes('/information'));

        if (!isSearched)
            setInputValue('')
        else {
            handleSearch(selectedItem)
        }
    }, [location.pathname, isSearched, ascendingOrder]);

    const handleSearch = async (itemName) => {
        let reportService = new ReportService();
        setSelectedItemName(itemName)
        dispatch(setActivePage(1))
        try {
            setError(null);

            if (itemName === 'laborantName') {
                await laborantNameSchema.validate({ laborantName: inputValue });

                dispatch(setLoading(true))
                dispatch(setIsSearched(true))

                let laborantName = inputValue.split(' ');
                const firstName = laborantName[0];
                const lastName = laborantName.slice(1).join(' ');

                const result = await reportService.getByLaborantName(firstName, lastName, activePage - 1, pageSize, !ascendingOrder);

                dispatch(setTotalPages(Math.ceil(result.data.content.length / pageSize)));
                dispatch(setReports(result.data.content))
                dispatch(setLoading(false))

            } else if (itemName === 'patientName') {
                await patientNameSchema.validate({ patientName: inputValue });

                dispatch(setLoading(true))
                dispatch(setIsSearched(true))

                let patientName = inputValue.split(' ');
                const firstName = patientName[0];
                const lastName = patientName.slice(1).join(' ');

                const result = await reportService.getByPatientName(firstName, lastName, activePage - 1, pageSize, !ascendingOrder);

                dispatch(setTotalPages(Math.ceil(result.data.content.length / pageSize)))
                dispatch(setReports(result.data.content))
                dispatch(setLoading(false))

            } else if (itemName === 'patientId') {
                await patientIdSchema.validate({ patientId: inputValue });

                dispatch(setLoading(true))
                dispatch(setIsSearched(true))

                const result = await reportService.getByPatientIdNumber(inputValue, activePage - 1, pageSize, !ascendingOrder);

                dispatch(setTotalPages(Math.ceil(result.data.content.length / pageSize)))
                dispatch(setReports(result.data.content))
                dispatch(setLoading(false))

            }
        } catch (error) {
            console.error("Hata:", error.message);
            setError(error.message);
            setTimeout(() => {
                setError(null)
            }, 1000)
        }
    }

    return (
        <div>
            <Menu vertical>
                {isReportsPage ? (
                    <>
                        <Menu.Item onClick={() => navigate(`/laborant/${laborantId}`)}>
                            <Icon name='arrow left' /> Back
                        </Menu.Item>
                    </>
                ) :
                    <>
                        <Menu.Item>
                            <Input icon='search' placeholder='Search...' value={inputValue} onChange={(e, { value }) => setInputValue(value)} />
                        </Menu.Item>
                        <Menu.Item onClick={() => handleSearch('laborantName')}>
                            By Laborant Name
                        </Menu.Item>

                        <Menu.Item onClick={() => handleSearch('patientName')}>
                            By Patient Name
                        </Menu.Item>

                        <Menu.Item onClick={() => handleSearch('patientId')}>
                            By Patient Id
                        </Menu.Item>

                        {error && (
                            <Message negative>
                                <Message.Header>Error</Message.Header>
                                <p>{error}</p>
                            </Message>
                        )}
                    </>}
            </Menu>

        </div>
    )

}
