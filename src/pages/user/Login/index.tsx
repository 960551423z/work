import {
  LockOutlined,
  UserOutlined,
} from '@ant-design/icons';
import {Alert, message, Tabs} from 'antd';
import React, {useState} from 'react';
import {ProFormText, LoginForm} from '@ant-design/pro-form';
import {history, useModel} from 'umi';
import Footer from '@/components/Footer';
import {login} from '@/services/ant-design-pro/api';
import styles from './index.less';
import {ProFormCheckbox} from "@ant-design/pro-form/es";



const LoginMessage: React.FC<{
  content: string;
}> = ({content}) => (
  <Alert
    style={{
      marginBottom: 24,
    }}
    message={content}
    type="error"
    showIcon
  />
);

const Login: React.FC = () => {
  const [userLoginState] = useState<API.LoginResult>({});
  const [type, setType] = useState<string>('account');
  const {initialState, setInitialState} = useModel('@@initialState');

  const fetchUserInfo = async () => {
    const userInfo = await initialState?.fetchUserInfo?.();

    if (userInfo) {
      await setInitialState((s) => ({...s, currentUser: userInfo}));
    }
  };

  const handleSubmit = async (values: API.LoginParams) => {
    try {
      // 登录
      const msg = await login({...values, type});


      if (msg.code === 0) {
        const defaultLoginSuccessMessage = '登录成功！';
        message.success(defaultLoginSuccessMessage);
        await fetchUserInfo();
        /** 此方法会跳转到 redirect 参数所在的位置 */

        if (!history) return;
        const {query} = history.location;
        const {redirect} = query as {
          redirect: string;
        };
        history.push(redirect || '/');
        return;
      } else {
        message.error(msg.description); // 如果失败去设置用户错误信息
      }
    } catch (error) {
      message.error('登录失败，请重试！');
    }
  };

  const {status, type: loginType} = userLoginState;
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <LoginForm
          logo={<img alt="logo" src="/logo.svg"/>}
          title="小 张 用 户 中 心"
          subTitle={'提供一套完整的用户中心系统'}
          initialValues={{
            autoLogin: true,
          }}

          onFinish={async (values) => {
            await handleSubmit(values as API.LoginParams);
          }}
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane key="account" tab={'账户密码登录'}/>
          </Tabs>

          {/* 登录*/}
          {status === 'error' && loginType === 'account' && (
            <LoginMessage content={'错误的用户名和密码'}/>
          )}
          {type === 'account' && (
            <>
              <ProFormText
                name="userAccount"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请输入用户名'}
                rules={[
                  {
                    required: true,
                    message: '用户名是必填项！',
                  },
                  {
                    min: 4,
                    type: 'string',
                    message: '用户名长度不小于4位'
                  }
                ]}
              />
              <ProFormText.Password
                name="userPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请输入密码'}
                rules={[
                  {
                    required: true,
                    message: '密码是必填项！',
                  }, {
                    min: 6,
                    type: 'string',
                    message: '密码长度不小于6位'
                  }

                ]}
              />
            </>
          )}

          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              自动登录
            </ProFormCheckbox>
            <a
              style={{
                float: 'right',
              }}
              target="_blank" href={'https://wpa.qq.com/msgrd?v=3&uin=960551423&site=qq&menu=yes'} rel="noreferrer"

            >
              点击联系管理员
            </a>

            <a style={{
              float: 'right',
              marginRight: '15px'
            }}
            href={'http://localhost:8000/user/register'}
            >
              注册
            </a>
          </div>
        </LoginForm>
      </div>
      <Footer/>
    </div>
  );
};


export default Login;
