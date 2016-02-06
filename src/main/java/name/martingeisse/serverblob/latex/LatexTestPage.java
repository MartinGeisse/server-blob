/**
 * Copyright (c) 2015 Martin Geisse
 *
 * This file is distributed under the terms of the MIT license.
 */

package name.martingeisse.serverblob.latex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.util.time.Time;
import name.martingeisse.serverblob.console.wicket.MyWicketApplication;
import name.martingeisse.serverblob.console.wicket.page.AbstractPage;
import name.martingeisse.wicket.helpers.Iframe;

/**
 * Note: the source code is sent to LaTeX in ISO-8859-1 (latin1) encoding. LaTeX understands special
 * characters using this encoding by importing the inputenc package:
 *
 *     \\usepackage[latin1]{inputenc}
 *
 */
public class LatexTestPage extends AbstractPage {

	private String sourceCode;
	private byte[] pdf;
	private String latexError;

	/**
	 * Constructor.
	 */
	public LatexTestPage() {
		final Form<Void> form = new Form<>("form");
		form.add(new TextArea<>("sourceCode", new PropertyModel<>(this, "sourceCode")));
		form.add(new AjaxButton("submit") {

			// override
			@Override
			protected void onSubmit(final AjaxRequestTarget ajaxRequestTarget, final Form<?> form) {
				pdf = null;
				latexError = null;
				if (sourceCode != null) {
					final LatexService latexService = MyWicketApplication.get().getDependency(LatexService.class);
					final byte[] sourceCodeBytes = sourceCode.getBytes(StandardCharsets.ISO_8859_1);
					final InputStream sourceCodeStream = new ByteArrayInputStream(sourceCodeBytes);
					final ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
					final StringWriter errorWriter = new StringWriter();
					final boolean success;
					try {
						success = latexService.render(sourceCodeStream, pdfStream, errorWriter);
					} catch (final IOException e) {
						throw new RuntimeException(e);
					}
					if (success) {
						pdf = pdfStream.toByteArray();
					} else {
						latexError = errorWriter.toString();
					}
				}
				((Iframe)LatexTestPage.this.get("preview")).renderReloadScript(ajaxRequestTarget);
			}
		});
		add(form);
		add(new Iframe("preview", new AbstractReadOnlyModel<IResource>() {

			@Override
			public IResource getObject() {
				return new AbstractResource() {

					@Override
					protected ResourceResponse newResourceResponse(final Attributes attributes) {
						final ResourceResponse response = new ResourceResponse();
						response.setLastModified(Time.now());
						response.setContentDisposition(ContentDisposition.INLINE);
						if (pdf == null) {
							response.setContentType("text/plain");
							response.setWriteCallback(new WriteCallback() {
								@Override
								public void writeData(final Attributes attributes) throws IOException {
									if (latexError != null) {
										final Response response = attributes.getResponse();
										response.write(latexError);
									}
								}
							});
						} else {
							response.setContentType("application/pdf");
							response.setWriteCallback(new WriteCallback() {
								@Override
								public void writeData(final Attributes attributes) throws IOException {
									writeStream(attributes, new ByteArrayInputStream(pdf));
								}
							});
						}
						return response;
					}
				};
			}
		}));
	}

	/**
	 * Getter method for the sourceCode.
	 * @return the sourceCode
	 */
	public String getSourceCode() {
		return sourceCode;
	}

	/**
	 * Setter method for the sourceCode.
	 * @param sourceCode the sourceCode to set
	 */
	public void setSourceCode(final String sourceCode) {
		this.sourceCode = sourceCode;
	}

}
