export default function buildAuthHeader() {
  const token = localStorage.accessToken;
  return token ? `Bearer ${localStorage.accessToken}` : null;
}
