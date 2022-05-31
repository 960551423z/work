import {LockOutlined, UserOutlined,} from '@ant-design/icons';
import {message, Tabs} from 'antd';
import React, {useState} from 'react';
import {LoginForm, ProFormText} from '@ant-design/pro-form';
import {history} from 'umi';
import Footer from '@/components/Footer';
import {register} from '@/services/ant-design-pro/api';
import styles from './index.less';



const Register: React.FC = () => {
  const [type, setType] = useState<string>('account');


  const handleSubmit = async (values: API.RegisterParams) => {
    const {userPassword, checkPassword} = values
    // 校验，下面的表单只是帮助我们校验长度
    if (userPassword !== checkPassword) {
      message.error('两次密码不同,请重试');
    }

    try {
      // 注册
      const res = await register({...values, type});

      // 此时 data 为后端返回给前端的值
      if (res.code === 0 && res.data > 0) {
        const defaultLoginSuccessMessage = '注册成功！';
        message.success(defaultLoginSuccessMessage);
        /** 此方法会跳转到 redirect 参数所在的位置 */

        if (!history) return;
        const {query} = history.location;
        // 注册成功后跳转到登录页
        history.push({
          pathname: '/user/login',
          query
        });
        return;
      } else {
        // throw new Error(`register err id `)
        message.error(res.description);
      }
    } catch (error) {
      const defaultLoginFailureMessage = '注册失败，请重试！';
      message.error(defaultLoginFailureMessage);
    }
  };

  // @ts-ignore
  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <LoginForm
          submitter={{
            searchConfig: {
              submitText: '注册'
            }
          }}
          logo={<img alt="logo" src="/logo.svg"/>}
          title="小张用户中心"
          subTitle={'提供一套完整的用户中心系统'}
          initialValues={{
            autoLogin: true,
          }}

          onFinish={async (values) => {
            await handleSubmit(values as API.RegisterParams);
          }}
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane key="account" tab={'账户密码注册'}/>
          </Tabs>

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
                    message: '用户长度不小于4位'
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
                  },
                  {
                    min: 6,
                    type: 'string',
                    message: '长度不小于6位'
                  }
                ]}
              />
              <ProFormText.Password
                name="checkPassword"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon}/>,
                }}
                placeholder={'请输确认密码'}
                rules={[
                  {
                    required: true,
                    message: '确认密码是必填项！',
                  },
                  {
                    min: 6,
                    type: 'string',
                    message: '确认密码长度不小于6位'
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
          </div>
        </LoginForm>
      </div>
      <Footer/>
    </div>
  );
};


export default Register;
