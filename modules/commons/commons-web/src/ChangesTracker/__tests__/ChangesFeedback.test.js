import React from "react";
import { render, unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";

import {ChangesFeedback} from "../ChangesFeedback";
import {useStateValue} from "../ChangesTrackerProvider";

jest.mock("../ChangesTrackerProvider", () => ({
  useStateValue: jest.fn().mockImplementation(() => ({
    unsavedChanges: false
  }))
}));

global.Liferay = {
  Language: {
    get: jest.fn().mockImplementation((key) => key)
  }
};

let container = null;
beforeEach(() => {
  container = document.createElement("div");
  document.body.appendChild(container);

  useStateValue.mockClear();
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});

it("renders message based on unsavedChanges property", () => {
  act(() => {
    render(<ChangesFeedback />, container);
  });
  expect(container.querySelector(".icon-feedback")).toBeDefined();
  expect(container.querySelector(".text-feedback").textContent).toBe("no-changes-made");
  expect(useStateValue).toHaveBeenCalledTimes(1);

  useStateValue.mockImplementation(() => ({
    unsavedChanges: true
  }));

  act(() => {
    render(<ChangesFeedback />, container);
  });
  expect(container.querySelector(".icon-feedback")).toBeDefined();
  expect(container.querySelector(".changes-feedback").classList.contains('unsaved-changes')).toBe(true);
  expect(container.querySelector(".text-feedback").textContent).toBe("unsaved-changes");
  expect(useStateValue).toHaveBeenCalledTimes(2);
});