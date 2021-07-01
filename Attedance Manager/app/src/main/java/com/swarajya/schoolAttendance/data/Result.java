package com.swarajya.schoolAttendance.data;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.swarajya.schoolAttendance.data.model.LoggedInUser;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class Result<T> {
  // hide the private constructor to limit subclass types (Success, Error)
  private Result() {
  }

  @NonNull
  @Override
  public String toString() {
    if (this instanceof Result.Success) {
      Result.Success success = (Result.Success) this;
      return "Success[data=" + success.getData().toString() + "]";
    } else if (this instanceof Result.Error) {
      Result.Error error = (Result.Error) this;
      return "Error[exception=" + error.getError().toString() + "]";
    }
    return "";
  }

  // Success sub-class
  public final static class Success<T> extends Result {
    private final T data;

    public Success(T data) {
      this.data = data;
    }

    public T getData() {
      return this.data;
    }
  }

  // Error sub-class
  public final static class Error extends Result {
    @Nullable
    private final Exception error;

    public Error(Exception error) {
      this.error = error;
    }

    public Error(LoggedInUser fakeUser) {

      error = null;
    }

      @Nullable
      public Exception getError() {
      return this.error;
    }
  }
}
