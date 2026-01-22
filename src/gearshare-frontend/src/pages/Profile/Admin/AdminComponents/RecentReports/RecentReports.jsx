import { useState, useEffect } from 'react'

import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TableRow from '@mui/material/TableRow'

import { getActiveReports, updateReportStatus } from '../../../../../api/adminService'
import ReasonFullView from './ReasonFullView'
import ClientInfoDialog from './ClientInfoDialog'

function RecentReports() {
    
    const [reports, setReports] = useState([])
    const [selectedReport, setSelectedReport] = useState(null)

    const [reasonDialogOpen, setReasonDialogOpen] = useState(false)

    const [clientDialogOpen, setClientDialogOpen] = useState(false)
    const [selectedClient, setSelectedClient] = useState(null)

    useEffect(() => {
        const fetchReports = async () => {
            const data = await getActiveReports()
            setReports(data.map(normalizeReport))
        }

        fetchReports()
    }, [])

    const normalizeReport = (report) => ({
        reportUUID: report.reportUUID,

        reporter: {
            clientUUID: report.reporter.clientUUID,
            username: report.reporter.username,
            email: report.reporter.email
        },

        reportee: {
            clientUUID: report.reportee.clientUUID,
            username: report.reportee.username,
            email: report.reportee.email
        },

        reportDateTime: report.reportDateTime,
        reason: report.reason
    })

    const handleAcceptReport = async (report) => {
        await updateReportStatus(report.reportUUID, {
            ...report,
            outcome: true
        })

        setReports(prev =>
            prev.filter(r => r.reportUUID !== report.reportUUID)
        )

        setReasonDialogOpen(false)
        setSelectedReport(null)
    }

    const handleDismissReport = async (report) => {
        await updateReportStatus(report.reportUUID, {
            ...report,
            outcome: false
        })

        setReports(prev =>
            prev.filter(r => r.reportUUID !== report.reportUUID)
        )

        setReasonDialogOpen(false)
        setSelectedReport(null)
    }

    const columns = [
        { id: 'reportUUID', label: 'Report ID' },
        { id: 'reportee', label: 'Reported user' },
        { id: 'reporter', label: 'Reported by' },
        { id: 'date', label: 'Submission datetime' },
        { id: 'reason', label: 'Reason for reporting' }
    ]

    return (
        <>
            <TableContainer>
                <Table stickyHeader>
                    <TableHead>
                        <TableRow>
                            {columns.map(col => (
                                <TableCell key={col.id}>{col.label}</TableCell>
                            ))}
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {reports.map(report => (
                            <TableRow key={report.reportUUID}>
                                <TableCell>{report.reportUUID}</TableCell>

                                <TableCell
                                    onClick={() => {
                                        setSelectedClient(report.reportee)
                                        setClientDialogOpen(true)
                                    }}
                                    sx={{ cursor: 'pointer', color: 'primary.main', textDecoration: 'underline' }}
                                >
                                    {report.reportee.clientUUID}
                                </TableCell>

                                <TableCell
                                    onClick={() => {
                                        setSelectedClient(report.reporter)
                                        setClientDialogOpen(true)
                                    }}
                                    sx={{ cursor: 'pointer', color: 'primary.main', textDecoration: 'underline' }}
                                >
                                    {report.reporter.clientUUID}
                                </TableCell>

                                <TableCell>
                                    {new Date(report.reportDateTime).toLocaleString()}
                                </TableCell>

                                <TableCell
                                    onClick={() => {
                                        setSelectedReport(report)
                                        setReasonDialogOpen(true)
                                    }}
                                    sx={{ cursor: 'pointer', color: 'primary.main', textDecoration: 'underline' }}
                                >
                                    {report.reason.length > 30
                                        ? `${report.reason.slice(0, 30)}...`
                                        : report.reason}
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <ReasonFullView
                open={reasonDialogOpen}
                report={selectedReport}
                onClose={() => setReasonDialogOpen(false)}
                onAccept={handleAcceptReport}
                onDismiss={handleDismissReport}
            />

            <ClientInfoDialog
                open={clientDialogOpen}
                client={selectedClient}
                onClose={() => setClientDialogOpen(false)}
            />
        </>
    )
}

export default RecentReports
