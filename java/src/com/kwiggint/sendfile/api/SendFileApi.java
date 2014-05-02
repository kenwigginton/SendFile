package com.kwiggint.sendfile.api;

import com.kwiggint.sendfile.model.PendingFile;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

import java.util.Queue;

/**
 * Retrofitted API Interface.
 * Used to perform calls on the site's RESTful API endpoints.
 */
public interface SendFileApi { //TODO: Add authentication to service.
  @GET("/{user}/pending")
  Queue<PendingFile> pendingFiles(@Path("user") String user, @Header("password") String password);

  @DELETE("/{user}/pending")
  boolean removePendingFile(@Path("user") String user, @Header("password") String password);
}
