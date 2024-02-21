import React, { useState, useEffect } from 'react';
import { Icon, Menu, Table, Pagination, Loader } from 'semantic-ui-react';
import ReportService from '../services/reportService';
import { Link, useParams } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { setReports, setLoading, setActivePage, setPageSize, setAscendingOrder, setTotalPages } from '../store/features/reportList/reportsSlice';

export default function ReportList() {
    const reportService = new ReportService();

    const reports = useSelector(state => state.reports.reports);
    const loading = useSelector(state => state.reports.loading);
    const pageSize = useSelector(state => state.reports.pageSize);
    const activePage = useSelector(state => state.reports.activePage);
    const ascendingOrder = useSelector(state => state.reports.ascendingOrder);
    const isSearched = useSelector(state => state.reports.isSearched);
    const totalPages = useSelector(state => state.reports.totalPages);

    const dispatch = useDispatch();
    const { laborantId } = useParams();

    useEffect(() => {
        if (!isSearched) {
            dispatch(setLoading(true));
            reportService.getReportTotalPages(pageSize).then(result => {
                dispatch(setTotalPages(result.data))
            });
            if (ascendingOrder) {
                reportService.getByDescendingReport(activePage - 1, pageSize).then(result => {
                    dispatch(setReports(result.data.content));
                    dispatch(setLoading(false));
                });
            } else {
                reportService.getByAscendingReport(activePage - 1, pageSize).then(result => {
                    dispatch(setReports(result.data.content));
                    dispatch(setLoading(false));
                });
            }
        }
    }, [isSearched, ascendingOrder, activePage, dispatch, pageSize]);

    const toggleSortOrder = () => {
        dispatch(setAscendingOrder(!ascendingOrder));
    };

    const formatDate = (inputDate) => {
        if (!inputDate) return '';

        const [year, month, day] = inputDate.split('-');
        return `${day}-${month}-${year}`;
    };

    return (

        <div className="custom">
            <Table celled>

                <Table.Header>
                    <Table.Row>
                        <Table.HeaderCell>File Number</Table.HeaderCell>
                        <Table.HeaderCell>Patient Name</Table.HeaderCell>
                        <Table.HeaderCell>Patient Surname</Table.HeaderCell>
                        <Table.HeaderCell>Identification Number</Table.HeaderCell>
                        <Table.HeaderCell>Diagnosis Title</Table.HeaderCell>
                        <Table.HeaderCell>Diagnosis Detail</Table.HeaderCell>
                        <Table.HeaderCell onClick={toggleSortOrder}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                <span>Report Date</span>
                                <Icon name={`chevron ${ascendingOrder ? 'down' : 'up'}`} />
                            </div>
                        </Table.HeaderCell>
                        <Table.HeaderCell>Report Image</Table.HeaderCell>
                        <Table.HeaderCell>Laborant Name</Table.HeaderCell>
                    </Table.Row>
                </Table.Header>

                <Table.Body>
                    {loading ? (
                        <Table.Row>
                            <Table.Cell colSpan="9" style={{ textAlign: 'center' }}>
                                <Loader active={loading} inline='centered' />
                            </Table.Cell>
                        </Table.Row>
                    ) : (
                        reports.map((report) => (
                            <Table.Row key={report.id}>
                                <Table.Cell><Link to={`/laborant/${laborantId}/reports/${report.id}`}>{report.fileNumber}</Link></Table.Cell>
                                <Table.Cell>{report.patientFirstName}</Table.Cell>
                                <Table.Cell>{report.patientLastName}</Table.Cell>
                                <Table.Cell>{report.patientIdNumber}</Table.Cell>
                                <Table.Cell>{report.diagnosisTitle}</Table.Cell>
                                <Table.Cell>{report.diagnosisDetails}</Table.Cell>
                                <Table.Cell>{formatDate(report.reportDate)}</Table.Cell>
                                <Table.Cell>
                                    <a href={`data:image/png;base64,${report.reportPictureBase64}`} target="_blank" rel="noopener noreferrer">
                                        <img src={`data:image/png;base64,${report.reportPictureBase64}`} style={{ maxWidth: '100px', height: 'auto' }} />
                                    </a>
                                </Table.Cell>
                                <Table.Cell>{report.laborantFirstName} {report.laborantLastName}</Table.Cell>
                            </Table.Row>
                        ))
                    )}
                </Table.Body>

                <Table.Footer>
                    <Table.Row>
                        <Table.HeaderCell colSpan='9'>
                            <Menu floated='right' pagination>
                                <Pagination
                                    activePage={activePage}
                                    totalPages={totalPages}
                                    onPageChange={(e, { activePage }) => dispatch(setActivePage(activePage))}
                                />
                            </Menu>
                        </Table.HeaderCell>
                    </Table.Row>
                </Table.Footer>
            </Table>
        </div>
    );
}
