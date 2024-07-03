import React from 'react';
import DOMPurify from 'dompurify';
import parse from 'html-react-parser';

/*
 * This function converts strings into react components. It is an XSS-safe alternative to dangerouslySetInnerHTML
 * which allows the use of liferay message keys when keys are using basic html tags like <strong>.
 */
export function parseAsHTML (message) {
	const cleanHTML = DOMPurify.sanitize(message);

	return parse(cleanHTML);
}