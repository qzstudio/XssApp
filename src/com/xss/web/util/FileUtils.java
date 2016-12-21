package com.xss.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.aspectj.util.FileUtil;

public class FileUtils {
	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param folderName
	 * @param fileName
	 * @param content
	 */
	public static void writeFile(final String folderName,
			final String fileName, final String content) {
		if (content == null || content.isEmpty()) {
			return;
		}
		FileWriter writer = null;
		File file = null;
		try {
			file = new File(folderName);
			if (!file.exists()) {
				// 文件夹不存在，创建
				file.mkdir();
			}
			file = new File(folderName + "/" + fileName);
			// System.out.println(folderName+"/"+fileName);
			if (!file.exists()) {
				// 文件夹不存在，创建
				file.createNewFile();
			}
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(folderName + "/" + fileName, true);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	public static void write(String path, String context) {
		write(path, context, "utf-8");
	}
	public static void write(String path, String context, String encode) {
		OutputStream pt = null;
		try {
			pt = new FileOutputStream(path);
			pt.write(context.getBytes(encode));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pt.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void writeAppend(String path, String context){
		writeAppend(path, context, "utf-8");
	}
	public static void writeAppend(String path, String context, String encode) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path, true)));
			out.write(context);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void writeAppendLine(String path, String context){
		writeAppendLine(path, context, "utf-8");
	}
	public static void writeAppendLine(String path, String context, String encode){
		writeAppend(path, context+"\r\n", encode);
	}
	public static String readFile(String path) {
		return readFile(path, "utf-8");
	}
	public static String readFile(String path, String encode) {
		InputStreamReader read = null;
		FileInputStream in = null;
		BufferedReader bufferedReader = null;
		try {

			File file = new File(path);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				in = new FileInputStream(file);
				read = new InputStreamReader(in, encode);// 考虑到编码格式
				bufferedReader = new BufferedReader(read);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line).append("\r\n");
				}
				return sb.toString();
			}
		} catch (Exception e) {
		} finally {
			try {
				bufferedReader.close();
				read.close();
				in.close();
			} catch (Exception e) {
			}
		}
		return null;
	}
	public static byte[] readFileByte(String path) {
		try {
			File file = new File(path);  
	        long fileSize = file.length();  
	        if (fileSize > Integer.MAX_VALUE) {  
	            System.out.println("file too big...");  
	            return null;  
	        }  
	        FileInputStream fi = new FileInputStream(file);  
	        byte[] buffer = new byte[(int) fileSize];  
	        int offset = 0;  
	        int numRead = 0;  
	        while (offset < buffer.length  
	        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
	            offset += numRead;  
	        }  
	        // 确保所有数据均被读取  
	        if (offset != buffer.length) {  
	        throw new IOException("Could not completely read file "  
	                    + file.getName());  
	        }  
	        fi.close();  
	        return buffer;  
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取目录下所有文件(按时间排序)
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getFileSort(String path) {

		List<File> list = getFiles(path, new ArrayList<File>());

		if (list != null && list.size() > 0) {

			Collections.sort(list, new Comparator<File>() {
				public int compare(File file, File newFile) {
					if (file.lastModified() < newFile.lastModified()) {
						return 1;
					} else if (file.lastModified() == newFile.lastModified()) {
						return 0;
					} else {
						return -1;
					}

				}
			});

		}

		return list;
	}

	/**
	 * 
	 * 获取目录下所有文件
	 * 
	 * @param realpath
	 * @param files
	 * @return
	 */
	public static List<File> getFiles(String realpath, List<File> files) {

		File realFile = new File(realpath);
		if (realFile.isDirectory()) {
			File[] subfiles = realFile.listFiles();
			for (File file : subfiles) {
				if (file.isDirectory()) {
					getFiles(file.getAbsolutePath(), files);
				} else {
					files.add(file);
				}
			}
		}
		return files;
	}

	public static void rewrite(File file, String data) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static List<String> readList(File file) {
		BufferedReader br = null;
		List<String> data = new ArrayList<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			for (String str = null; (str = br.readLine()) != null;) {
				data.add(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	public static void writeFile(String path, byte[] content) {
		try {
			FileOutputStream fos = new FileOutputStream(path);

			fos.write(content);
			fos.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}


}
