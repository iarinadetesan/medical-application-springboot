
import Table from 'react-bootstrap/Table';
import Badge from 'react-bootstrap/Badge';
import "../../styles/ReportContainerTable.css"
import { getStatusBadgeVariant } from '../../assets/utils/StatusBadgeUtils';
export default function PatientPrescriprionsContainer({objects}) {

const documents = objects || [];

    return(

        <div className="report-container table-responsive">
                <div className="report-header">
                    <h1 className="recent-Articles">Retete medicale recente</h1>
                    <button className="view">View All</button>
                </div>
                        
            
            <Table responsive className="report-body" striped bordered hover>
            <thead >
              <tr >
                <th className="t-op ">Data</th>
                <th className="t-op ">Medicament</th>
                <th className="t-op ">Dozaj</th>

                <th className="t-op">Medic</th>
                <th  className="t-op">Instructiuni</th>
                <th  className="t-op">Valabil până la data de</th>
                <th className="t-op ">Status</th>

              </tr>
            </thead>
            <tbody className="items">
              {documents.length === 0 ? (
  <p className="empty-message">Nu există rețete.</p>
) : (
              documents.map((prescription) => (
                <tr className="item" key={prescription.id}>
                  
              
                  <td className="t-op-nextlvl"> {prescription.issueDate}</td>
                  <td className="t-op-nextlvl">{prescription.medicationName}</td>
                  <td className="t-op-nextlvl">{prescription.dosage}</td>
                  <td className="t-op-nextlvl">{prescription.doctorName}</td>
                  <td className="t-op-nextlvl">{prescription.instructions}</td>
                  
                  <td className="t-op-nextlvl"> {prescription.expiryDate}</td>
                  <td className="t-op-nextlvl"><Badge bg={getStatusBadgeVariant(prescription.status)}>{prescription.status} </Badge></td>
                 
                </tr>
              )))}
            </tbody>
          </Table> 
                    </div>
                
    );
}