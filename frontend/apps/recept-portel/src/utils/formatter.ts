const statusColors: Record<string, { name: string; color: string }> = {
  IN_PROGRESS: {
    name: "IN PROGRESS",
    color: "#FFD701",
  },
  PENDING: {
    name: "PENDING",
    color: "#000000",
  },
  COMPLETED: {
    name: "COMPLETED",
    color: "#16A34A",
  },
  CANCELLED: {
    name: "CANCELLED",
    color: "#DC2626",
  },
};

export function respectiveColor(status: string) {
  return (
    statusColors[status] ?? {
      name: status,
      color: "#80808 0",
    }
  );
}
