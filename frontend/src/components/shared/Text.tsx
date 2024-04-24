import { CSSProperties } from 'react'
import { colors, Colors } from '@styles/colorPalette'
import { Typography, typographyMap } from '@styles/typography'

import { useRecoilValue } from 'recoil'
import { themeState } from '@/stores/common'

import styled from '@emotion/styled'

interface TextProps {
  typography?: Typography
  color?: Colors
  display?: CSSProperties['display']
  textAlign?: CSSProperties['textAlign']
  fontWeight?: CSSProperties['fontWeight']
  bold?: boolean
}

const Text = styled.span<TextProps>(
  ({
    color = useRecoilValue(themeState) === 'dark' ||
    useRecoilValue(themeState) === 'contrast'
      ? 'white'
      : 'black',
    display,
    textAlign,
    fontWeight,
    bold,
  }) => ({
    color: colors[color],
    display,
    textAlign,
    fontWeight: bold ? 'bold' : fontWeight,
  }),
  ({ typography = 't5' }) => typographyMap[typography],
)

export default Text
