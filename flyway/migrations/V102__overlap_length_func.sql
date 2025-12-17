CREATE OR REPLACE FUNCTION overlap_length(arr1 text[], arr2 text[])
    RETURNS bigint AS
$$
SELECT count(*)
FROM (SELECT unnest(arr1)
      INTERSECT
      SELECT unnest(arr2)
      ORDER BY 1) _intersect;
$$ LANGUAGE sql
    IMMUTABLE
    STRICT;
