import React, { useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useForm, FieldErrors } from 'react-hook-form';
import { Box } from '@mui/material';

import { ReactComponent as InfoSVG } from './../images/info.svg';
import { ISearchField, ISearchFieldValues } from '../types/components/SearchBar.type';

function SearchBox() {
  return (
    <Box display="flex" alignItems="center" gap={2} padding="0 24px 15px 24px">
      <SearchField placeholder="공고나 지역을 검색해보세요" />
      <button>
        <InfoSVG />
      </button>
    </Box>
  );
}

export default React.memo(SearchBox);

function SearchFieldComponent({ placeholder }: ISearchField) {
  const navigate = useNavigate();

  const { register, handleSubmit } = useForm<ISearchFieldValues>({
    defaultValues: {
      query: '',
    },
  });

  const onSubmit = useCallback((data: ISearchFieldValues) => {
    console.log(data);
  }, []);

  const onError = useCallback((errors: FieldErrors<ISearchFieldValues>) => {
    const { query } = errors;

    if (query?.message) {
      console.error(`>>> ${query.message}`);
    }
  }, []);

  return (
    <form className="search-box" onSubmit={handleSubmit(onSubmit, onError)} autoComplete="off">
      <input className="search-box__input" type="text" placeholder={placeholder} {...register('query', { required: '검색어를 입력해주세요.' })} />
    </form>
  );
}

export const SearchField = React.memo(SearchFieldComponent);
