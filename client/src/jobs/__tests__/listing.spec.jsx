import * as React from 'react';
import { create } from 'react-test-renderer';
import Listing from '~/jobs/listing';

// Couldn't get the tests to work when using typescript. Renaming the file extension to 'jsx'.

test('Renders empty list of jobs', () => {
  const data = [];
  const tree = create(<Listing data={data} />).toJSON();
  expect(tree).toMatchSnapshot();
});

test('Renders a list with one job', () => {
  const data = [
    {
      id: 100,
      job_title: "Accountant",
      min_salary: 8000.00,
      max_salary: 9000.00
    }
  ];
  const component = create(<Listing data={data} />).toJSON();
  expect(tree).toMatchSnapshot();
});


test('Renders a list with more than one job', () => {
  const data = [
    {
      id: 10,
      job_title: "DBA",
      min_salary: 5000.00,
      max_salary: 5500.00
    },
    {
      id: 11,
      job_title: "Accountant",
      min_salary: 4000.00,
      max_salary: 6000.00
    }
  ];
  const component = create(<Listing data={data} />).toJSON();
  expect(tree).toMatchSnapshot();
});
