import React, { useState, useEffect } from 'react'

import Paper from '@mui/material/Paper'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'

import { getActiveSuspensions } from '../../../../../api/adminService'
import TimeLeftCell from './TimeLeftCell'

function ActiveSuspensions() {
    const [suspensions, setSuspensions] = useState([])

    useEffect(() => {
        const fetchSuspensions = async () => {
            const data = await getActiveSuspensions()
            setSuspensions(data.map(normalizeSuspension))
        }

        fetchSuspensions()
    }, [])

    const normalizeSuspension = (suspension) => ({
        suspensionUUID: suspension.suspensionUUID,
        client: {
            clientUUID: suspension.client.clientUUID,
            username: suspension.client.username,
            email: suspension.client.email
        },
        suspensionStartDateTime: suspension.suspensionStartDateTime,
        suspensionLength: suspension.suspensionLength
    })

    const columns = [
        { id: 'suspensionUUID', label: 'Suspension ID' },
        { id: 'client', label: 'Suspended user' },
        { id: 'date', label: 'Suspended at' },
        { id: 'length', label: 'Length (days)' },
        { id: 'timeLeft', label: 'Time Left' }
    ]

    return (
        <TableContainer component={Paper}>
            <Table stickyHeader>
                <TableHead>
                    <TableRow>
                        {columns.map(col => (
                            <TableCell key={col.id}>{col.label}</TableCell>
                        ))}
                    </TableRow>
                </TableHead>

                <TableBody>
                    {suspensions.map(suspension => (
                        <TableRow key={suspension.suspensionUUID}>
                            <TableCell>{suspension.suspensionUUID}</TableCell>

                            <TableCell>
                                {suspension.client.username}
                            </TableCell>

                            <TableCell>
                                {new Date(
                                    suspension.suspensionStartDateTime
                                ).toLocaleString()}
                            </TableCell>

                            <TableCell>
                                {suspension.suspensionLength}
                            </TableCell>

                            <TimeLeftCell suspension={suspension} />
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default ActiveSuspensions
