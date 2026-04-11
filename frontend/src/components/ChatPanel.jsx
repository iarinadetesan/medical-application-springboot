import { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Offcanvas from 'react-bootstrap/Offcanvas';
import Form from 'react-bootstrap/Form';

function ChatPanel() {
  const [showChat, setShowChat] = useState(false);

  return (
    <>
      <Button
        onClick={() => setShowChat(true)}
        className="position-fixed bottom-0 end-0 m-4"
      >
        Chat
      </Button>

      <Offcanvas
        show={showChat}
        onHide={() => setShowChat(false)}
        placement="end"
      >
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>Chat</Offcanvas.Title>
        </Offcanvas.Header>

        <Offcanvas.Body className="d-flex flex-column p-0">
          <div className="flex-grow-1 overflow-auto p-3 border-bottom">
            <div className="mb-2 p-2 border rounded">Bună ziua!</div>
            <div className="mb-2 p-2 border rounded">Cu ce vă pot ajuta?</div>
          </div>

          <Form className="p-3 d-flex gap-2">
            <Form.Control type="text" placeholder="Scrie un mesaj..." />
            <Button variant="primary">Send</Button>
          </Form>
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
}

export default ChatPanel;