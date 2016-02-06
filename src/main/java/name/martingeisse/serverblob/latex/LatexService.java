/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.latex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.WriterOutputStream;
import name.martingeisse.serverblob.util.TemporaryFolder;

/**
 * Service used to run LaTeX.
 */
public class LatexService {

	/**
	 * Renders a LaTeX source file to PDF.
	 *
	 * The input is passed as an {@link InputStream} which will be read to obtain the
	 * input LaTeX file. The input is intentionally not passed as a {@link Reader} because
	 * the encoding is significant for LaTeX -- it's bytes, not characters, that are
	 * handled by it.
	 *
	 * The output PDF will be written to the specified {@link OutputStream}.
	 * 
	 * Errors will be sent to a {@link Writer}. Since LaTeX sends a byte stream for errors,
	 * another encoding is used here. This one is always ISO-8859-1 since it's much more
	 * important to indicate *where* an error has occurred than preserving all special
	 * characters. Especially, using UTF-8 here runs the risk of decoding errors
	 * ("invalid UTF-8 sequence") that break error output.
	 *
	 * @param sourceStream the stream from which the source file gets read
	 * @param pdfStream the stream to which the rendered PDF gets written
	 * @param errorWriter the writer to which error messages get written.
	 * @return true on success, false on failure
	 * @throws IOException on I/O errors
	 */
	public boolean render(final InputStream sourceStream, final OutputStream pdfStream, final Writer errorWriter) throws IOException {
		return render(sourceStream, pdfStream, new WriterOutputStream(errorWriter, StandardCharsets.ISO_8859_1));
	}
	
	/**
	 * Like {@link #render(InputStream, OutputStream, Writer)}, but takes an {@link OutputStream} for the
	 * error output.
	 * 
	 * @param sourceStream the stream from which the source file gets read
	 * @param pdfStream the stream to which the rendered PDF gets written
	 * @param errorStream the stream to which error messages get written.
	 * @return true on success, false on failure
	 * @throws IOException on I/O errors
	 */
	public boolean render(final InputStream sourceStream, final OutputStream pdfStream, final OutputStream errorStream) throws IOException {
		boolean success;
		try (TemporaryFolder folder = new TemporaryFolder()) {
			File sourceFile = new File(folder.getInstanceFolder(), "document.tex");
			FileUtils.copyInputStreamToFile(sourceStream, sourceFile);
			CommandLine commandLine = new CommandLine("/Library/TeX/texbin/pdflatex");
			commandLine.addArgument("-halt-on-error");
			commandLine.addArgument(sourceFile.getName());
			DefaultExecutor executor = new DefaultExecutor() {
				@Override
				public boolean isFailure(int exitValue) {
					return false;
				}
			};
			executor.setWorkingDirectory(folder.getInstanceFolder());
			executor.setStreamHandler(new PumpStreamHandler(errorStream));
			int exitCode = executor.execute(commandLine);
			success = (exitCode == 0);
			File outputFile = new File(folder.getInstanceFolder(), "document.pdf");
			if (outputFile.exists()) {
				FileUtils.copyFile(outputFile, pdfStream);
			}
		}
		return success;
	}


	/**
	 * TODO remove
	 * 
	 * @param args command-line arguments
	 * @throws IOException ...
	 */
	public static void main(String[] args) throws IOException {
		try (FileInputStream in = new FileInputStream(new File("resource/test/test.tex"))) {
			try (FileOutputStream out = new FileOutputStream(new File("resource/test/test.pdf"))) {
				new LatexService().render(in, out, System.out);
			}
		}
	}

}
