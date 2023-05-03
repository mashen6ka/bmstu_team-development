export default function (timestamp) {
  return new Date(timestamp * 1000)
    .toLocaleString()
    .slice(0, -3)
    .replaceAll("/", "-")
    .replaceAll(",", "");
}
