import api from './axios'

export async function getActiveReports() {
    try {
        const response = await api.get('/admin/reports/active');
        return response.data;
    } catch (error) {
        console.error('Failed to fetch active reports:', error);
        return [];
    }
}

export async function updateReportStatus(reportUUID, reportDto) {
    try {
        const response = await api.patch(
            `/admin/reports/active/${reportUUID}`,
            reportDto
        );
        return response.data;
    } catch (error) {
        console.error(
            `Failed to update report with UUID ${reportUUID}:`,
            error
        );
        return null;
    }
}

export async function getActiveSuspensions() {
    try {
        const response = await api.get('/admin/suspensions/active');
        return response.data;
    } catch (error) {
        console.error('Failed to fetch active suspensions:', error);
        return [];
    }
}
