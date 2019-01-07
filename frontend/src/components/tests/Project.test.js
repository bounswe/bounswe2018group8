// @mehmetcalim: tests functions of project page.
import React from 'react';
import { shallow, mount, render } from 'enzyme';
import Project from '../Project.js';
import '../../setupTests.js';

// @mehmetcalim: describes what we are testing

describe('<Project />', () => {
  it('renders once submit function', () => {
    const wrapper = shallow(<Project />, { lifecycleExperimental: true });
    const spyCDM = jest.spyOn(Project.prototype, 'submit');
    expect('submit');
  });
});






