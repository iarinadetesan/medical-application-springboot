import Badge from 'react-bootstrap/Badge';
import "../styles/ReportContainer.css"
export default function ReportContainer() {
    return(

        <div className="report-container">
                <div className="report-header">
                    <h1 className="recent-Articles">Recent Articles</h1>
                    <button className="view">View All</button>
                </div>

                <div className="report-body">
                    <div className="report-topic-heading">
                        <h3 className="t-op">Article</h3>
                        <h3 className="t-op">Views</h3>
                        <h3 className="t-op">Comments</h3>
                        <h3 className="t-op">Status</h3>
                    </div>

                    <div className="items">
                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 73</h3>
                            <h3 className="t-op-nextlvl">2.9k</h3>
                            <h3 className="t-op-nextlvl">210</h3>
                            <Badge variant="success">Published</Badge>
                        </div>

                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 72</h3>
                            <h3 className="t-op-nextlvl">1.5k</h3>
                            <h3 className="t-op-nextlvl">360</h3>
                            <h3 className="t-op-nextlvl label-tag">Published</h3>
                        </div>

                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 71</h3>
                            <h3 className="t-op-nextlvl">1.1k</h3>
                            <h3 className="t-op-nextlvl">150</h3>
                            <h3 className="t-op-nextlvl label-tag">Published</h3>
                        </div>

                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 70</h3>
                            <h3 className="t-op-nextlvl">1.2k</h3>
                            <h3 className="t-op-nextlvl">420</h3>
                            <h3 className="t-op-nextlvl label-tag">Published</h3>
                        </div>

                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 69</h3>
                            <h3 className="t-op-nextlvl">2.6k</h3>
                            <h3 className="t-op-nextlvl">190</h3>
                            <h3 className="t-op-nextlvl label-tag">Published</h3>
                        </div>

                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 68</h3>
                            <h3 className="t-op-nextlvl">1.9k</h3>
                            <h3 className="t-op-nextlvl">390</h3>
                            <h3 className="t-op-nextlvl label-tag">Published</h3>
                        </div>

                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 67</h3>
                            <h3 className="t-op-nextlvl">1.2k</h3>
                            <h3 className="t-op-nextlvl">580</h3>
                            <h3 className="t-op-nextlvl label-tag">Published</h3>
                        </div>

                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 66</h3>
                            <h3 className="t-op-nextlvl">3.6k</h3>
                            <h3 className="t-op-nextlvl">160</h3>
                            <h3 className="t-op-nextlvl label-tag">Published</h3>
                        </div>

                        <div className="item1">
                            <h3 className="t-op-nextlvl">Article 65</h3>
                            <h3 className="t-op-nextlvl">1.3k</h3>
                            <h3 className="t-op-nextlvl">220</h3>
                            <h3 className="t-op-nextlvl label-tag">Published</h3>
                        </div>
                    </div>
                </div>
                
            </div>
    );
}