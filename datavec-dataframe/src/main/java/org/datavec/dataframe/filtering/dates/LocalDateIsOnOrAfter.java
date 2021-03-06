package org.datavec.dataframe.filtering.dates;


import org.datavec.dataframe.api.DateColumn;
import org.datavec.dataframe.api.Table;
import org.datavec.dataframe.columns.ColumnReference;
import org.datavec.dataframe.filtering.ColumnFilter;
import org.datavec.dataframe.util.Selection;

import javax.annotation.concurrent.Immutable;

/**
 *
 */
@Immutable
public class LocalDateIsOnOrAfter extends ColumnFilter {

  private int value;

  public LocalDateIsOnOrAfter(ColumnReference reference, int value) {
    super(reference);
    this.value = value;
  }

  @Override
  public Selection apply(Table relation) {

    DateColumn dateColumn = (DateColumn) relation.column(columnReference().getColumnName());
    return dateColumn.isOnOrAfter(value);
  }
}
