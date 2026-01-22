import { useState } from "react"
import RecentReports from './AdminComponents/RecentReports/RecentReports'
import ActiveSuspensions from './AdminComponents/ActiveSuspensions/ActiveSuspensions'

import { ChevronLeft, ChevronRight } from "lucide-react";

function AdminDashboardMain() {

    const tables = [
        {
            title: "Nedavne prijave",
            component: <RecentReports />
        },
        {
            title: "Aktivne suspenzije",
            component: <ActiveSuspensions />
        }
        // ,
        // {
        //     title: "Nedavna isključenja",
        //     component: <RecentPermabans />
        // }
    ]

    const [step, setStep] = useState(0)


    const prevStep = () => {
        setStep(prev => (prev - 1 + tables.length) % tables.length)
    }

    const nextStep = () => {
        setStep(prev => (prev + 1 + tables.length) % tables.length)
    }

    return (
        <div className="admin-dashboard-container">
            <h2>Pregledajte nedavnu korisničku aktivnost na platformi</h2>
            <div className="dashboard-inner-container">

                <button onClick={prevStep}> <ChevronLeft size={20} /> </button>
                <button onClick={nextStep}> <ChevronRight size={20} /> </button>
                <div className="step">
                    <h3>{tables[step].table}</h3>
                    {tables[step].component}
                </div>

            </div>
        </div>
    )
}

export default AdminDashboardMain