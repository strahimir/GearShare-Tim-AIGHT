import { Dialog, DialogTitle, DialogContent, Typography } from '@mui/material'
import { useEffect, useState } from 'react'

function SuspensionTimeDialog({
    open,
    onClose,
    suspensionStartDateTime,
    suspensionLength,
    username
}) {
    const [now, setNow] = useState(Date.now())

    useEffect(() => {
        if (!open) return

        const interval = setInterval(() => {
            setNow(Date.now())
        }, 1000)

        return () => clearInterval(interval)
    }, [open])

    const start = new Date(suspensionStartDateTime).getTime()
    const end = start + suspensionLength * 24 * 60 * 60 * 1000
    const remainingMs = end - now

    let display
    if (remainingMs <= 0) {
        display = 'Expired'
    } else {
        const totalSeconds = Math.floor(remainingMs / 1000)
        const days = Math.floor(totalSeconds / 86400)
        const hours = Math.floor((totalSeconds % 86400) / 3600)
        const minutes = Math.floor((totalSeconds % 3600) / 60)
        const seconds = totalSeconds % 60

        display = `${days}d ${hours}h ${minutes}m ${seconds}s`
    }

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Suspension Time Remaining</DialogTitle>
            <DialogContent>
                <Typography variant="subtitle2">
                    User: {username}
                </Typography>
                <Typography variant="h6" sx={{ mt: 2 }}>
                    {display}
                </Typography>
            </DialogContent>
        </Dialog>
    )
}

export default SuspensionTimeDialog
