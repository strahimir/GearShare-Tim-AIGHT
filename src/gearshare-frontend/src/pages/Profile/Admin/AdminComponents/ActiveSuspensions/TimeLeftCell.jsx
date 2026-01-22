import { useState } from 'react'
import { TableCell } from '@mui/material'
import SuspensionTimeDialog from './SuspensionTimeDialog'

function TimeLeftCell({ suspension }) {
    const [open, setOpen] = useState(false)

    return (
        <>
            <TableCell
                onClick={() => setOpen(true)}
                sx={{
                    cursor: 'pointer',
                    color: 'primary.main',
                    textDecoration: 'underline'
                }}
            >
                View
            </TableCell>

            <SuspensionTimeDialog
                open={open}
                onClose={() => setOpen(false)}
                suspensionStartDateTime={suspension.suspensionStartDateTime}
                suspensionLength={suspension.suspensionLength}
                username={suspension.client.username}
            />
        </>
    )
}

export default TimeLeftCell
