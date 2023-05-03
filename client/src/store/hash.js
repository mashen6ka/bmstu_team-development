import { SHA256, enc } from "crypto-js";

export default function hash(string) {
  return SHA256(string).toString(enc.Hex);
}
