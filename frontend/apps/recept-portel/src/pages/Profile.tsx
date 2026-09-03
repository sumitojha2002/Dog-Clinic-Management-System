import React from "react";

function Profile() {
  const timestamp = Date.now();
  const date = new Date(timestamp);

  const formatted = new Intl.DateTimeFormat("en-US", {
    month: "short",
    day: "numeric",
  }).format(date);
  return (
    <div className="p-5 pl-15 pr-10">
      <div className="text-5xl">Profile</div>
      <span className="text-[15px] text-gray-600 ">
        Front desk · today, {formatted}
      </span>
    </div>
  );
}

export default Profile;
