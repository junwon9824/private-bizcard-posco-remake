// import styled from '@emotion/styled'
// import { colors } from '@/styles/colorPalette'
import { useMutation } from '@tanstack/react-query'
import { userReg } from '@/apis/auth'
import { useRecoilState, useSetRecoilState } from 'recoil'
import { isUserinStorageState, userState } from '@/stores/user'
import { useContext, useEffect } from 'react'
import { TeamsFxContext } from './Context'
import { useData } from '@microsoft/teamsfx-react'
import { Spinner } from '@fluentui/react-spinner'

const Tutorial = () => {
  const setIsUserinStorage = useSetRecoilState(isUserinStorageState)
  const [user, setUser] = useRecoilState(userState)

  // 유저 정보 조회
  const { teamsUserCredential } = useContext(TeamsFxContext)
  useData(async () => {
    if (teamsUserCredential) {
      const userInfo = await teamsUserCredential.getUserInfo()
      setUser({
        name: userInfo.displayName,
        email: userInfo.preferredUserName,
      })
      return userInfo
    }
  })

  // 유저 등록
  const { mutate } = useMutation({
    mutationKey: ['userReg'],
    mutationFn: userReg,
    onSuccess(result) {
      setUser(prev => ({ ...prev, userId: result.data_body }))
      console.log('등록 성공:', result)
    },
    onError(error) {
      console.error('등록 실패:', error)
    },
  })

  useEffect(() => {
    let userData = {
      name: user.name,
      email: user.email,
    }

    mutate(userData)
    setIsUserinStorage(true)
  }, [mutate, user])

  return (
    <Spinner label="로딩 중..." style={{ height: '100vh' }} />
    // <Container>
    //   <Box>
    //     <p>환영합니다</p>
    //     <p>BizCard 명합 관리 앱을 시작합니다</p>
    //     <Button onClick={() => HandleUserReg()}>시작하기</Button>
    //   </Box>
    // </Container>
  )
}

export default Tutorial

// style

// const Container = styled.div`
//   height: 100vh;
//   display: flex;
//   justify-content: center;
//   align-items: center;
// `

// const Button = styled.button`
//   position: relative;
//   padding: 5px 10px;
//   border-radius: 10px;
//   background-color: ${colors.teamsBG6};
//   color: #fff;
// `

// const Box = styled.div`
//   position: relative;
//   text-align: center;
//   padding: 30px;

//   ::before {
//     content: '';
//     opacity: 0.3;
//     position: absolute;
//     background-color: ${colors.themeText};
//     border-radius: 20px;
//     top: 0px;
//     left: 0px;
//     right: 0px;
//     bottom: 0px;
//   }

//   p {
//     position: relative;
//     color: #fff;
//     margin-bottom: 20px;
//   }
// `
