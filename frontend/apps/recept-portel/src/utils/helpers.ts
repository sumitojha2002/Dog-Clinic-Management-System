import { z } from "zod";

export function decodeToken<T = Record<string, unknown>>(
  token: string,
): T | null {
  try {
    const payload = token.split(".")[1];
    const decode = atob(payload);
    return JSON.parse(decode);
  } catch {
    return null;
  }
}

export const dogInfoSchema = z.object({
  name: z.string().min(1, "Name is requried").max(50, "Name is to Long"),
  breed: z.string().min(1, "Breed is required"),
  dateOfBirth: z.string().min(1, "Date is required"),
  gender: z.enum(["male", "female"], {
    message: "Please select a gender",
  }),
  imageUrl: z
    .custom<FileList>()
    .refine(
      (files) => files && files.length > 0,
      "Profile picture is required",
    ),
});

export const AppointmentSchema = z.object({
  reason: z
    .string()
    .min(1, "Reason is required")
    .max(50, "Reason should be short"),
  appointmentDate: z.string().min(1, "Date is required"),
  appointmentTime: z.string().min(1, "Time is required"),
  vetId: z.string().min(1, "Vet needs to be selected"),
});

export const dogProfileUpdate = z.object({
  name: z.string().min(1, "Name is requried"),
  breed: z.string().min(1, "Breed is required"),
  color: z.string().min(1, "Color is required"),
  dateOfBirth: z.string().min(1, "Date of birth is required"),
  gender: z.enum(["male", "female"]),
  imageUrl: z.custom<FileList>().optional(),
});

export const tagsBasedColor = (status: string) => {
  const appoitmenstatus: Record<string, string> = {
    PENDING: "text-yellow-500",
    CONFIRMED: "text-green-500",
    CHECKED_IN: "text-green-500",
    IN_PROGRESS: "text-green-500",
  };
  return appoitmenstatus[status] ?? "text-gray-500";
};

export const loginSchema = z.object({
  username: z
    .string()
    .max(50, "Username is too long")
    .min(1, "Username is required.")
    .refine((name) => !name.includes(" "), {
      message: "Username should not contain space",
    }),

  password: z.string(),
});
