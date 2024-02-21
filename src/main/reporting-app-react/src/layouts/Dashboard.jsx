import React from 'react'
import Navi from './Navi'
import ReportList from '../pages/ReportList'
import { Grid } from 'semantic-ui-react'
import { Route, Routes, useParams } from 'react-router-dom';
import ReportDetail from '../pages/ReportDetail';
import { Container } from 'semantic-ui-react'
import AddMenu from './AddMenu';
import ReportRegistration from '../pages/ReportRegistration'
import LaborantRegistrationUpdate from '../pages/LaborantRegistrationUpdate';

export default function Dashboard() {

    return (
        <div>
            <AddMenu />
            <Container style={{ width: '1450px' }} className="main">
                <Grid>
                    <Grid.Row>
                        <Grid.Column width={2}>
                            <Navi />
                        </Grid.Column>
                        <Grid.Column width={14}>
                            <Routes>
                                <Route path="/" element={<ReportList />} />
                                <Route path="/reports/:id/" element={<ReportDetail />} />
                                <Route path="/reportRegistration" element={<ReportRegistration />} />
                                <Route path="/information" element={<LaborantRegistrationUpdate />} />
                            </Routes>
                        </Grid.Column>
                    </Grid.Row>
                </Grid>
            </Container>
        </div>
    )
}
