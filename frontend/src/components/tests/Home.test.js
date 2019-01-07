// @mehmetcalim: tests functions of homepage.
import React from 'react';
import { shallow, mount, render } from 'enzyme';
import Home from '../Home.js';
import '../../setupTests.js';

// @mehmetcalim: describes what we are testing

describe('<Home />', () => {
  it('renders once submit function', () => {
    const wrapper = shallow(<Home />, { lifecycleExperimental: true });
    const spyCDM = jest.spyOn(Home.prototype, 'submit');
    expect('submit');
  });
  it('renders once lifecycle component', () => {
    const wrapper = shallow(<Home />, { lifecycleExperimental: true });
    const spyCDM = jest.spyOn(Home.prototype, 'componentDidMount');
    expect('componentDidMount');
  });
});






