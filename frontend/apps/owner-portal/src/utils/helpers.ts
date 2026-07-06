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

export const signupSchema = z.object({
  username: z
    .string()
    .max(50, "Name is to long")
    .min(1, "Name is required")
    .refine((name) => !name.includes(" "), {
      message: "Name should not contain space",
    }),

  password: z
    .string()
    .min(5, "Password must be at least 5 characters")
    .max(20, "Password is long"),

  email: z.string().email("Enter a valid email"),
});
