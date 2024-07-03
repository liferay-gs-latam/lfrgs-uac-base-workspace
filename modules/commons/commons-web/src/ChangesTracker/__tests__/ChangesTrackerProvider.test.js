import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";

import {ChangesTrackerContext, ChangesTrackerProvider} from "../ChangesTrackerProvider";

let container = null;
beforeEach(() => {
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("renders child components", () => {
  act(() => {
    render(
      <ChangesTrackerProvider>
        <div className="children-container">
          <div className="child-1" />
          <div className="child-2" />
        </div>
      </ChangesTrackerProvider>
      , container);
  });

  let childrenContainer = container.querySelector(".children-container");
  expect(childrenContainer).toBeDefined();
  expect(childrenContainer.querySelector(".child-1")).toBeDefined();
  expect(childrenContainer.querySelector(".child-2")).toBeDefined();
});

it("updates unsavedChanges state when onChange is called", () => {
  act(() => {
    render(
      <ChangesTrackerProvider>
        <ChangesTrackerContext.Consumer>
          {({unsavedChanges, onChange}) => (
            <div className="test-wrapper">
              <span className="test-text">{unsavedChanges ? 'Unsaved Changes' : 'No Changes Detected'}</span>
              <button className="onchange-trigger" onClick={onChange}>Trigger Change</button>
            </div>
          )}
        </ChangesTrackerContext.Consumer>
      </ChangesTrackerProvider>
      , container);
  });

  let testWrapper = container.querySelector('.test-wrapper');
  let testText = testWrapper.querySelector('.test-text');

  expect(testText.textContent).toBe('No Changes Detected');

  testWrapper.querySelector('.onchange-trigger').click();

  expect(testText.textContent).toBe('Unsaved Changes');
});