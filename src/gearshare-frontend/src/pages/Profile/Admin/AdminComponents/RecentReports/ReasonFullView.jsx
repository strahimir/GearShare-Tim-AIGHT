import Dialog from '@mui/material/Dialog'
import DialogTitle from '@mui/material/DialogTitle'
import DialogContent from '@mui/material/DialogContent'
import DialogActions from '@mui/material/DialogActions'
import Button from '@mui/material/Button'

function ReasonFullView({ open, report, onClose, onAccept, onDismiss }) {
    return (
        <Dialog
            open={open}
            onClose={onClose}
            maxWidth="sm"
            fullWidth
        >
            <DialogTitle>
                Razlog prijave:
            </DialogTitle>

            <DialogContent dividers>
                <p>{report?.reason}</p>
            </DialogContent>

            <DialogActions>
                <Button
                    color="error"
                    onClick={() => onDismiss(report)}
                >
                    Odbaci
                </Button>

                <Button
                    color="success"
                    variant="contained"
                    onClick={() => onAccept(report)}
                >
                    Odobri
                </Button>

                <Button onClick={onClose}>
                    Zatvori
                </Button>
            </DialogActions>
        </Dialog>
    )
}

export default ReasonFullView
