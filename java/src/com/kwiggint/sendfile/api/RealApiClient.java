package com.kwiggint.sendfile.api;

import com.kwiggint.sendfile.ConfigValue;
import com.kwiggint.sendfile.SendFileApp;
import com.kwiggint.sendfile.model.PendingFile;
import org.bouncycastle.crypto.digests.SHA224Digest;
import retrofit.RestAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.util.Queue;

/** Real Client that performs API calls and handles responses. */
@Singleton
public class RealApiClient implements ApiClient {
  //TODO: Make this secure and get a real domain.
  private final String apiUrl;
  private final RestAdapter restAdapter;
  private SendFileApi sendFileApi;
  private Queue<PendingFile> pendingFiles;

  @Inject public RealApiClient(@ConfigValue("api_endpoint_url") String apiUrl) {
    this.apiUrl = apiUrl;
    restAdapter = new RestAdapter.Builder().setEndpoint(apiUrl).build();
    sendFileApi = restAdapter.create(SendFileApi.class);
  }

  @Override public boolean hasPendingTransfers() {
    if (pendingFiles.isEmpty())
      pendingFiles = sendFileApi.pendingFiles(SendFileApp.USER, SendFileApp.PASSWORD.toString());
    return !pendingFiles.isEmpty();
  }

  @Override public PendingFile getNextPendingFile() {
    return null;
  }

  @Override public boolean removePendingFile(PendingFile pendingFile) {
    return false;
  }
}
