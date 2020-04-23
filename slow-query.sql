EXPLAIN ANALYSE SELECT a.value AS "Account Number", MAX(f0.value) AS "Company Name", MAX(f2.value) AS "Patient Full Name", MAX(f3.value) AS "Patient DOB", MAX(f4.value) AS "Patient SSN", MAX(f5.value) AS "Guarantor Relationship", MAX(f6.value) AS "Guarantor Full Name", MAX(f7.value) AS "Guarantor DOB", MAX(f8.value) AS "Guarantor Gender", MAX(f9.value) AS "Guarantor SSN", MAX(f10.value) AS "Guarantor State", MAX(f11.value) AS "Guarantor Address 1", MAX(f12.value) AS "Guarantor Address 2", MAX(f13.value) AS "Guarantor City", MAX(f14.value) AS "Guarantor Zip", MAX(f15.value) AS "Guarantor Home Phone", MAX(f16.value) AS "Guarantor Work Phone", MAX(f17.value) AS "Guarantor Cell Phone", MAX(f18.value) AS "Guarantor Language", MAX(f19.value) AS "Guarantor Employer", MAX(f20.value) AS "Service Date", MAX(f21.value) AS "Provider Full Name", MAX(f22.value) AS "Procedure Name", MAX(f23.value) AS "Original Charge", SUM(f24.value::MONEY::NUMERIC) AS "Bad Debt Amount" FROM (SELECT data_field.data_row_id, data_field.value FROM data_field, field, data_row WHERE field.id = 1648 AND data_row.data_id = 269 AND data_field.data_row_id = data_row.id AND  data_field.field_id = field.id AND data_field.value != field.label) AS a,data_field AS f0, data_field AS f2, data_field AS f3, data_field AS f4, data_field AS f5, data_field AS f6, data_field AS f7, data_field AS f8, data_field AS f9, data_field AS f10, data_field AS f11, data_field AS f12, data_field AS f13, data_field AS f14, data_field AS f15, data_field AS f16, data_field AS f17, data_field AS f18, data_field AS f19, data_field AS f20, data_field AS f21, data_field AS f22, data_field AS f23, data_field AS f24 WHERE f0.data_row_id = a.data_row_id AND f0.field_id = 1647 AND f2.data_row_id = a.data_row_id AND f2.field_id = 1649 AND f3.data_row_id = a.data_row_id AND f3.field_id = 1650 AND f4.data_row_id = a.data_row_id AND f4.field_id = 1651 AND f5.data_row_id = a.data_row_id AND f5.field_id = 1652 AND f6.data_row_id = a.data_row_id AND f6.field_id = 1653 AND f7.data_row_id = a.data_row_id AND f7.field_id = 1654 AND f8.data_row_id = a.data_row_id AND f8.field_id = 1655 AND f9.data_row_id = a.data_row_id AND f9.field_id = 1656 AND f10.data_row_id = a.data_row_id AND f10.field_id = 1660 AND f11.data_row_id = a.data_row_id AND f11.field_id = 1657 AND f12.data_row_id = a.data_row_id AND f12.field_id = 1658 AND f13.data_row_id = a.data_row_id AND f13.field_id = 1659 AND f14.data_row_id = a.data_row_id AND f14.field_id = 1661 AND f15.data_row_id = a.data_row_id AND f15.field_id = 1662 AND f16.data_row_id = a.data_row_id AND f16.field_id = 1663 AND f17.data_row_id = a.data_row_id AND f17.field_id = 1664 AND f18.data_row_id = a.data_row_id AND f18.field_id = 1665 AND f19.data_row_id = a.data_row_id AND f19.field_id = 1666 AND f20.data_row_id = a.data_row_id AND f20.field_id = 1667 AND f21.data_row_id = a.data_row_id AND f21.field_id = 1668 AND f22.data_row_id = a.data_row_id AND f22.field_id = 1669 AND f23.data_row_id = a.data_row_id AND f23.field_id = 1670 AND f24.data_row_id = a.data_row_id AND f24.field_id = 1671 GROUP BY a.value;

/*
    399 DATA ROWS

 Planning time: 588.501 ms
 Execution time: 2576134.279 ms
(224 rows)

 Planning time: 290.593 ms
 Execution time: 2363348.868 ms
(225 rows)


*/

EXPLAIN ANALYSE SELECT data_field.value AS "Account Number" FROM data_field, field, data_row WHERE field.id = 1648 AND data_row.data_id = 269 AND data_field.data_row_id = data_row.id AND  data_field.field_id = field.id AND data_field.value != field.label GROUP BY data_field.value;

/*
    399 DATA ROWS

 Planning time: 0.869 ms
 Execution time: 103.199 ms
(26 rows)
*/

EXPLAIN ANALYSE SELECT 
    MAX(f0.value) AS "Company Name", 
    MAX(f2.value) AS "Patient Full Name",
    /*(SELECT MAX(value) FROM data_field WHERE data_row_id = a.data_row_id AND field_id = 1649) AS "Patient Full Name",*/
    /*b.value AS "Patient Full Name",*/
    MAX(f3.value) AS "Patient DOB", MAX(f4.value) AS "Patient SSN", MAX(f5.value) AS "Guarantor Relationship", MAX(f6.value) AS "Guarantor Full Name", MAX(f7.value) AS "Guarantor DOB", MAX(f8.value) AS "Guarantor Gender", MAX(f9.value) AS "Guarantor SSN", MAX(f10.value) AS "Guarantor State", MAX(f11.value) AS "Guarantor Address 1", MAX(f12.value) AS "Guarantor Address 2", MAX(f13.value) AS "Guarantor City", MAX(f14.value) AS "Guarantor Zip", MAX(f15.value) AS "Guarantor Home Phone", MAX(f16.value) AS "Guarantor Work Phone", MAX(f17.value) AS "Guarantor Cell Phone", MAX(f18.value) AS "Guarantor Language", MAX(f19.value) AS "Guarantor Employer", MAX(f20.value) AS "Service Date", MAX(f21.value) AS "Provider Full Name", MAX(f22.value) AS "Procedure Name", MAX(f23.value) AS "Original Charge", SUM(f24.value::MONEY::NUMERIC) AS "Bad Debt Amount"
FROM 
    /*(
    SELECT
        data_field.data_row_id, data_field.value 
    FROM */ data_field, field, data_row ,
    /*WHERE field.id = 1648 AND data_row.data_id = 269 AND data_field.data_row_id = data_row.id AND  data_field.field_id = field.id AND data_field.value != field.label AND data_field.value = '9946'
) AS a,*/
    /*(SELECT MAX(value) FROM data_field WHERE field_id = 1649) as b,*/
    data_field AS f0, data_field AS f2, data_field AS f3, data_field AS f4, data_field AS f5, data_field AS f6, data_field AS f7, data_field AS f8, data_field AS f9, data_field AS f10, data_field AS f11, data_field AS f12, data_field AS f13, data_field AS f14, data_field AS f15, data_field AS f16, data_field AS f17, data_field AS f18, data_field AS f19, data_field AS f20, data_field AS f21, data_field AS f22, data_field AS f23, data_field AS f24 
WHERE 
    field.id = 1648 AND data_row.data_id = 269 AND data_field.data_row_id = data_row.id AND  data_field.field_id = field.id AND data_field.value != field.label AND data_field.value = '9946' AND
    /*b.data_row_id = a.data_row_id AND */
    f0.data_row_id = data_field.data_row_id AND f0.field_id = 1647 AND 
    f2.data_row_id = data_field.data_row_id AND f2.field_id = 1649 AND 
    f3.data_row_id = data_field.data_row_id AND f3.field_id = 1650 AND 
    f4.data_row_id = data_field.data_row_id AND f4.field_id = 1651 AND 
    f5.data_row_id = data_field.data_row_id AND f5.field_id = 1652 AND 
    f6.data_row_id = data_field.data_row_id AND f6.field_id = 1653 AND 
    f7.data_row_id = data_field.data_row_id AND f7.field_id = 1654 AND 
    f8.data_row_id = data_field.data_row_id AND f8.field_id = 1655 AND 
    f9.data_row_id = data_field.data_row_id AND f9.field_id = 1656 AND 
    f10.data_row_id = data_field.data_row_id AND f10.field_id = 1660 AND 
    f11.data_row_id = data_field.data_row_id AND f11.field_id = 1657 AND 
    f12.data_row_id = data_field.data_row_id AND f12.field_id = 1658 AND 
    f13.data_row_id = data_field.data_row_id AND f13.field_id = 1659 AND 
    f14.data_row_id = data_field.data_row_id AND f14.field_id = 1661 AND 
    f15.data_row_id = data_field.data_row_id AND f15.field_id = 1662 AND 
    f16.data_row_id = data_field.data_row_id AND f16.field_id = 1663 AND 
    f17.data_row_id = data_field.data_row_id AND f17.field_id = 1664 AND 
    f18.data_row_id = data_field.data_row_id AND f18.field_id = 1665 AND 
    f19.data_row_id = data_field.data_row_id AND f19.field_id = 1666 AND 
    f20.data_row_id = data_field.data_row_id AND f20.field_id = 1667 AND 
    f21.data_row_id = data_field.data_row_id AND f21.field_id = 1668 AND 
    f22.data_row_id = data_field.data_row_id AND f22.field_id = 1669 AND 
    f23.data_row_id = data_field.data_row_id AND f23.field_id = 1670 AND 
    f24.data_row_id = data_field.data_row_id AND f24.field_id = 1671;

 GROUP BY a.value;



