import React, { useState } from 'react'
import { Container, Dropdown, Menu } from 'semantic-ui-react'
import { useNavigate, useParams } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setIsSearched } from '../store/features/reportList/reportsSlice';


export default function AddMenu() {

    const loggedInClient = JSON.parse(localStorage.getItem("loggedInClient"));

    let { laborantId } = useParams()

    const navigate = useNavigate();

    const dispatch = useDispatch();

    const handleClick = () => {
        navigate(`/laborant/${laborantId}/reportRegistration`)
    };

    const handleClickExit = () => {
        localStorage.clear();
        navigate('/')
    };

    const handleClickAllReports = () => {
        dispatch(setIsSearched(false))
        navigate(`/laborant/${laborantId}`)

    };

    const handleClickInformation = () => {
        navigate(`/laborant/${laborantId}/information`)
    };

    return (
        <div>
            <Menu inverted fixed="top">
                <Container>
                    {
                        parseInt(localStorage.getItem('userId'), 10) === -1 ?
                            (
                                <Menu.Item onClick={handleClickAllReports}>
                                    All Reports
                                </Menu.Item>) : (
                                <>
                                    <Menu.Item onClick={handleClick}>
                                        Add Report
                                    </Menu.Item>

                                    <Menu.Item onClick={handleClickAllReports}>
                                        All Reports
                                    </Menu.Item>
                                </>
                            )
                    }
                    <Menu.Item position='right'>
                        <Dropdown pointing="top left" text={`${loggedInClient.firstName} ${loggedInClient.lastName}`}>

                            {localStorage.getItem('name') !== 'Admin' ? (
                                <Dropdown.Menu>
                                    <Dropdown.Item text="Account Information" onClick={handleClickInformation} />
                                    <Dropdown.Item text="Log Out" onClick={handleClickExit} />
                                </Dropdown.Menu>) : (
                                <Dropdown.Menu>
                                    <Dropdown.Item text="Log Out" onClick={handleClickExit} />
                                </Dropdown.Menu>
                            )
                            }
                        </Dropdown>
                    </Menu.Item>
                </Container>
            </Menu>

        </div>
    )
}
