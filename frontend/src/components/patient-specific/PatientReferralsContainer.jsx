
import Table from 'react-bootstrap/Table';
import Badge from 'react-bootstrap/Badge';

import "../../styles/ReportContainerTable.css"
import { getStatusBadgeVariant } from '../../assets/utils/StatusBadgeUtils';
export default function PatientReferralsContainer({objects}) {

const documents = objects || [];

    return(

        <div className="report-container table-responsive">
                <div className="report-header">
                    <h1 className="recent-Articles">Trimiteri medicale</h1>
                    <button className="view">View All</button>
                </div>
                        
            
            <Table responsive className="report-body" striped bordered hover>
            <thead >
              <tr >
                <th className="t-op ">Data</th>
                <th className="t-op">Doctor</th>
                <th  className="t-op">Specializare</th>
                <th  className="t-op">Motiv / Detalii</th>
                <th  className="t-op">Valabil până la data de</th>
                <th className="t-op ">Status</th>
              </tr>
            </thead>
            <tbody className="items">
              {documents.length === 0 ? (
  <p className="empty-message">Nu există trimiteri.</p>
) : (
              objects.map((referral) => (
                <tr className="item" key={referral.id}>
                  
                  
                 
                  
                  <td className="t-op-nextlvl">{referral.issueDate.substring(0,10) || "-"}</td>
                  
                  <td className="t-op-nextlvl">
                    {referral.referringDoctorName}
                  </td>
                  <td className="t-op-nextlvl">{referral.specialization || "Fără specializare"}</td>
                  <td className="t-op-nextlvl">
                    {referral.reason}
                  </td>
                  <td className="t-op-nextlvl">{referral.expiryDate.substring(0,10)}</td>
                  <td className="t-op-nextlvl"> <Badge bg={getStatusBadgeVariant(referral.status)}>
                   {referral.status || "Fără status"}</Badge>
                  </td>
                </tr>
              )))}
            </tbody>
          </Table> 
                    </div>
                
    );
}