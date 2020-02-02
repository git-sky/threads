package cn.com.sky.thread_patterns.serial_thread_confinement.demo;

import io.github.viscent.mtia.util.Debug;
import io.github.viscent.mtia.util.Tools;
import org.apache.commons.net.ftp.FTPClient;

public class FakeWorkerThread extends WorkerThread {

    public FakeWorkerThread(String outputDir, String ftpServer, String userName,
                            String password, String servWorkingDir) throws Exception {
        super(outputDir, ftpServer, userName, password, servWorkingDir);
    }

    @Override
    protected FTPClient initFTPClient(String ftpServer, String userName,
                                      String password) throws Exception {
        FTPClient ftpClient = new FTPClient();
        return ftpClient;
    }

    @Override
    protected void doRun() throws Exception {
        String file = workQueue.take();
        try {
            Debug.info("Download file %s", file);
            Tools.randomPause(80, 50);
        } finally {
            terminationToken.reservations.decrementAndGet();
        }

    }

}