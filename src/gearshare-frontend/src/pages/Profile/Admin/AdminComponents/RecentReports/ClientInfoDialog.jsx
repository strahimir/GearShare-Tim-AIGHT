import Dialog from '@mui/material/Dialog'
import DialogTitle from '@mui/material/DialogTitle'
import DialogContent from '@mui/material/DialogContent'
import DialogActions from '@mui/material/DialogActions'
import Button from '@mui/material/Button'

function ClientInfoDialog({ open, client, onClose }) {
    return (
        <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
            <DialogTitle>Korisnički podaci:</DialogTitle>

            <DialogContent dividers>
                <p><strong>UUID:</strong> {client?.clientUUID}</p>
                <p><strong>Username:</strong> {client?.username}</p>
                <p><strong>Email:</strong> {client?.email}</p>
            </DialogContent>

            <DialogActions>
                <Button onClick={onClose}>Close</Button>
            </DialogActions>
        </Dialog>
    )
}

export default ClientInfoDialog
