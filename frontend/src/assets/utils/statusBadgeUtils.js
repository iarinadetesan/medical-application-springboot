
export function getStatusBadgeVariant(status) {
  switch (status) {
    case "ACTIVE":
    case "PENDING":
      return "success";

    case "EXPIRED":
      return "danger";

    case "DISPENSED":
    case "COMPLETED":
      return "primary";

    default:
      return "secondary";
  }
}