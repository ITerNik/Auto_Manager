import { TextField, Typography } from "@mui/material";
import React from "react";

interface ProfileFieldProps {
  label: string;
  name: string;
  value: string;
  isEditing: boolean;
  register: any;
  error?: string;
  type?: string;
  disabled?: boolean;
  visible?: boolean;
}

export const ProfileField: React.FC<ProfileFieldProps> = ({
  label,
  name,
  value,
  isEditing = false,
  visible = true,
  register,
  disabled = false,
  error,
  type = "text",
}) => {
  if (!visible) {
    return null;
  }

  return isEditing ? (
    <TextField
      name={name}
      label={label}
      type={type}
      disabled={disabled}
      {...register}
      fullWidth
      margin="normal"
      error={!!error}
      helperText={error}
      InputLabelProps={type === "date" ? { shrink: true } : undefined}
    />
  ) : (
    <Typography variant="body1" className="mb-2">
      <strong>{label}:</strong> {value}
    </Typography>
  );
};
