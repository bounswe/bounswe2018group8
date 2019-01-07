// @mehmetcalim: tests componentDidMount function of profile page.
import React from 'react';
import { shallow, mount, render } from 'enzyme';
import Profile from '../Profile.js';
import '../../setupTests.js';

// @mehmetcalim: describes what we are testing

describe('<Profile />', () => {
  it('renders once <submit /> components', () => {
    const wrapper = shallow(<Profile />, { lifecycleExperimental: true });
    const spyCDM = jest.spyOn(Profile.prototype, 'componentDidMount');
    expect('componentDidMount');
  });
});

