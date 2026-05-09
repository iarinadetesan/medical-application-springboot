
import Table from 'react-bootstrap/Table';
import "../../styles/ReportContainerTable.css"
export default function PatientConsultationsContainer({objects}) {

const consultations = objects || [];


    return(

        <div className="report-container table-responsive">
                <div className="report-header">
                    <h1 className="recent-Articles">Consultații recente</h1>
                    <button className="view">View All</button>
                </div>
                        
            
            <Table responsive className="report-body" striped bordered hover>
            <thead >
              <tr >
                <th className="t-op ">Data</th>
                <th className="t-op">Doctor</th>
                <th  className="t-op">Diagnostic</th>
                <th  className="t-op">Scop / Detalii</th>
              </tr>
            </thead>
            <tbody className="items">
              {consultations.length === 0 ? (
  <p className="empty-message">Nu există consultații.</p>
) : (
  

              consultations.map((consultation) => (
                <tr className="item" key={consultation.id}>
                  
                  <td className="t-op-nextlvl">{consultation.consultationDate.substring(0,10) || "-"}</td>
                  <td className="t-op-nextlvl">
                    {consultation.doctorName}
                  </td>
                  <td className="t-op-nextlvl">
                    {
                      consultation.diagnosis ||
                      "-"}
                  </td>
                  <td className="t-op-nextlvl">
                    {consultation.notes}
                  </td>
                </tr>
              )))}
            </tbody>
          </Table> 
                    </div>
                
    );
}