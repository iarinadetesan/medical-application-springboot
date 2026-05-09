
export function getStatusBadgeVariant(status) {
  switch (status) {
    case "ACTIVE":
      return "success";
    case "APPROVED":
      return "success";
    case "COMPLETED":
      return "success";
    case "DISPENSED":
      return "success";
    
      

    case "EXPIRED":
      return "danger";
      case "REJECTED":
      return "danger";

    
    
    case "PENDING":
      return "primary";

    default:
      return "secondary";
  }
}