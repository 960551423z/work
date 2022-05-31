import type {ProSettings} from '@ant-design/pro-layout';
import {PageContainer, SettingDrawer} from '@ant-design/pro-layout';
import {Descriptions} from 'antd';
import React, {useState} from 'react';
// import {currentUser} from "@/services/ant-design-pro/api";
// import {history} from "@@/core/history";


// const user = async () => {
//   try {
//     let arr: any[] = [];
//     const newVar = await currentUser();
//     Object.keys(newVar).map((v,i) => {
//       arr[i] = newVar[v]
//     })
//     // @ts-ignore
//     return arr;
//
//   } catch (error) {
//     history.push('/welcome');
//   }
//   return undefined;
// };
// let a = t.user()
// console.log(user())

// user().then((res) => {
//   console.log(res[0])
// })

const Center: React.FC = () =>  {
  const [settings, setSetting] = useState<Partial<ProSettings> | undefined>({ fixSiderbar: true });
  // const [pathname, setPathname] = useState('/welcome')
  return (

    <div
      // id="test-pro-layout"
      style={{
        height: '70vh',
      }}
    >

        <PageContainer
      content={
      <Descriptions size="small" column={2}>
        <Descriptions.Item label="用户名">小张</Descriptions.Item>
        <Descriptions.Item label="性别">男</Descriptions.Item>
        <Descriptions.Item label="邮箱">960551423@qq.com</Descriptions.Item>
        <Descriptions.Item label="创建时间">2022-05-22</Descriptions.Item>
        <Descriptions.Item label="更新时间">2012-05-31</Descriptions.Item>
        <Descriptions.Item label="说明">
          <a>这块暂时不能动态修改，还没学到(后面学到再改)</a>
        </Descriptions.Item>
      </Descriptions>
          }

          tabList={[
            {
              tab: '基本信息',
              key: 'base',
            },
            {
              tab: '详细信息',
              key: 'info',
            },
          ]}
        >
        </PageContainer>
      <SettingDrawer
        settings={settings}
        onSettingChange={(changeSetting) => {
          setSetting(changeSetting);
        }}
        disableUrlParams={false}
      />
    </div>
  );
};

export default Center
