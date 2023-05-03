import jwt_decode from "jwt-decode";

export default function () {
  const token = localStorage.accessToken;
  return jwt_decode(token).id;
}
