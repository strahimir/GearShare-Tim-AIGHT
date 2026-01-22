import Dialog from '@mui/material/Dialog'
import DialogTitle from '@mui/material/DialogTitle'
import DialogContent from '@mui/material/DialogContent'
import DialogActions from '@mui/material/DialogActions'
import { Typography } from '@mui/material'
import Button from '@mui/material/Button'

function InfoDialog({ open, onClose }) {
    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>O pretraživanju pomoću lokacije</DialogTitle>

            <DialogContent>
                <Typography variant="h6" sx={{ mt: 2 }}>
                    Kako biste koristili funkcionalnost pretraživanja pomoću vaše trenutne lokacije:
                </Typography>

                <ul>
                    <li>
                        <Typography variant="body1">
                            Potrebno je u postavkama odobriti GearShare-u pristup lokaciji.
                        </Typography>
                    </li>
                    <li>
                        <Typography variant="body1">
                            Alternativno, možete ručno unijeti adresu u čijem okružju tražite oglase.
                        </Typography>
                    </li>
                </ul>
            </DialogContent>

            <DialogActions>
                <Button onClick={onClose}>Zatvori</Button>
            </DialogActions>
        </Dialog>
    )
}

export default InfoDialog
