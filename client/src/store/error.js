export class BaseError {
  message;
  status;

  constructor(status, message = null) {
    this.status = status;
    if (message) {
      this.message = message;
    } else {
      if (status === 403) this.message = "";
      else if (status === 500) this.message = "Internal server error";
      else this.message = "Unknown error. Try again later";
    }
  }
}

export class AuthError {
  message;
  status;

  constructor(status, message = null) {
    this.status = status;
    if (message) {
      this.message = message;
    } else {
      if (status === 403) this.message = "Incorrect login or password";
      else if (status === 409) this.message = "This login is already taken";
      else if (status === 500) this.message = "Internal server error";
      else this.message = "Unknown error. Try again later";
    }
  }
}
