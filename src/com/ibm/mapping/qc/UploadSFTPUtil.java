package com.ibm.mapping.qc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.ibm.mapping.util.createXSD;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * Util class to upload a file in to SFTP server.
 * 
 * @author Sanket Patil
 *
 */
public final class UploadSFTPUtil {
	/**
	 * Upload file to SFTP.
	 * 
	 * @param host
	 *            Host name of SFTP.
	 * @param port
	 *            Port name of SFTP.
	 * @param user
	 *            User name to connect SFTP.
	 * @param password
	 *            Password to connect SFTP.
	 * @param remoteDirectory
	 *            SFTP directory where files will be uploaded.
	 * @param localFile
	 *            Local file to upload.
	 * @throws IOException
	 * @throws SftpException 
	 */
	
	//log variable 
	static Logger log = Logger.getLogger(UploadSFTPUtil.class.getName());
			
	public static final boolean uploadFile(String host, int port, String user,
			String password, String remoteDirectory, String localFile)
			throws IOException, SftpException {

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;
		File f = null;
		FileInputStream fis = null;
		boolean gsa_auth=true;
		try {
			JSch jsch = new JSch();
			session = jsch.getSession(user, host, port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();

			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(remoteDirectory);
			f = new File(localFile);
			fis = new FileInputStream(f);
			channelSftp.put(fis, f.getName());
			System.out.println("File uplaoded successfuly.");
		}
		catch (com.jcraft.jsch.JSchException je) {
			je.printStackTrace();
			gsa_auth=false;
			log.error("GSA Authentication Failed!!!");
			return gsa_auth;
			//System.exit(0);
		}finally {
			if (fis != null) {
				fis.close();
			}
			try {
			channel.disconnect();
			session.disconnect();
			}
			catch(java.lang.NullPointerException ne) {
			log.error("Null pointer exception been raised due to invalid GSA credentials");
			}
		}		
		return gsa_auth;
	}
}