/** @jsxImportSource @emotion/react */
import { Field, tokens, SearchBox as FluentSearchBox } from '@fluentui/react-components'
import { css } from '@emotion/react'
import { useState } from 'react'

import Flex from './Flex'
import PeopleFilterSortIcons from './PeopleFilterSortIcons';
import { colors } from '@/styles/colorPalette';
import Spacing  from '@/components/shared/Spacing';

interface SearchBoxProps {
  placeholder?: string
  onChange?: (e: any) => void
  memberIcon?: boolean
  filterIcon?: boolean
  sortIcon?: boolean
  value: string
}

/**
 *
 * [searchValue, setSearchValue] = useState('');를
 * 각각 value와 onChange로 받아서 사용
 */
const SearchBox: React.FC<SearchBoxProps> = ({
  placeholder,
  filterIcon,
  memberIcon,
  onChange,
  value,
  sortIcon,
}) => {
  const handleKeyDown = (e: any): void => {
    if (e.key === 'Enter') {
      console.log('submit', value)
      // handleSubmit()
    } else {
      console.log('not enter')
    }
  }
  return (
    <div>
      <Spacing size={10} direction='vertical'/>
      <Flex justify="space-around" align="center" direction="row"
      style={{backgroundColor: tokens.colorNeutralBackground1}}>
        <div  css={searchBoxContainerCss}>
          <FluentSearchBox
            placeholder={placeholder}
            onKeyDown={handleKeyDown}
            onChange={onChange}
            css={searchBoxCss}
            appearance='filled-darker'
          />
        </div>
        <PeopleFilterSortIcons
         memberIcon={memberIcon}
         filterIcon={filterIcon}
         sortIcon={sortIcon}
        />
      </Flex>
      <Spacing size={30} direction='vertical'/>
      
    </div>
    
  )
}

export default SearchBox

// 사용 예
/**
 *
 *   const [searchValue, setSearchValue] = useState('');
 * return (
 *   <div>
 *     모바일 팀 명함
 *     <p></p>
 *     <SearchBox
 *     value={searchValue}
 *     onChange={(e:any) =>{ setSearchValue(e.target.value)
 *     console.log(searchValue)} }
 *
 *     />
 *   </div>
 * );
 */


const searchBoxCss = css`
  background-color: ${tokens.colorNeutralBackground1} !important;
.ms-SearchBox-clearButton {
  position: absolute;
    right: 0;
}
  border: none;
  border-bottom: none;
  width: 100%;
  background-color: ${tokens.colorNeutralBackground2};
`
const searchBoxContainerCss = css`
  min-width: 220px;
  background-color: ${tokens.colorNeutralBackground2};
  
`